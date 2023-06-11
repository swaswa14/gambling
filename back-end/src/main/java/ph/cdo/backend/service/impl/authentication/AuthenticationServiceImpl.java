package ph.cdo.backend.service.impl.authentication;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import ph.cdo.backend.dto.DTOEntity;
import ph.cdo.backend.dto.mapper.impl.registration.UserRegistrationMapper;
import ph.cdo.backend.entity.base.User;
import ph.cdo.backend.exceptions.*;
import ph.cdo.backend.repository.noBean.UserRepository;
import ph.cdo.backend.request.AuthenticationRequest;
import ph.cdo.backend.request.registration.BasicRegistrationForm;
import ph.cdo.backend.response.AuthenticationResponse;
import ph.cdo.backend.response.UserRegistrationResponse;
import ph.cdo.backend.service.*;
import ph.cdo.backend.service.impl.JwtService;
import ph.cdo.backend.service.impl.user.IUserService;


public abstract class AuthenticationServiceImpl<T extends User,  R extends DTOEntity
        , S extends IUserService<T, R>, U extends BasicRegistrationForm, V extends UserRegistrationMapper<T, U> ,
        F extends UserRepository<T>
        > implements AuthenticationService<U> {
    protected final F repository;
    protected final S service;
    protected final JwtService jwtService;
    protected final EmailService emailService;
    protected final PasswordEncoder passwordEncoder;
    protected final AuthenticationManager authenticationManager;
    protected final V mapper;
    public AuthenticationServiceImpl(F repository, S service, JwtService jwtService,
                                     EmailService emailService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, V mapper) {
        this.repository = repository;
        this.service = service;
            this.jwtService = jwtService;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.mapper = mapper;
    }

    @Override
    public UserRegistrationResponse registerClient(@Valid U request) {
        //Check if duplicate account
        final String email = request.getEmail();
        if(service.isEmailTaken(email)){
            throw new DuplicateEmailException(email);
            }
        //Encrypt the password first before it is saved in the DB!
        request.setPassword(passwordEncoder.encode(request.getPassword()));

        // Create a User Object to save.
         T user = mapper.apply(request);

        R registeredClient = service.save(user);

        T authenticatedClient =
                repository.findById(registeredClient.getID()).orElseThrow(UserRegistrationErrorException::new);

        if(registeredClient.getID() != null){

            String jwtToken = jwtService.generateToken(authenticatedClient);
            try {
                emailService.sendEmailEnableAccount(authenticatedClient);
            }catch (MessagingException e){
                throw new EmailErrorException(authenticatedClient.getEmail());
            }


            return UserRegistrationResponse.builder()
                    .header("Registration Successful")
                    .body("Please confirm your email to activate your account!")
                    .footer("Email will expired in 24 hours")
                    .email(authenticatedClient.getEmail())
                    .token(jwtToken)
                    .build();
        }
        else throw new UserRegistrationErrorException();


    }
    public AuthenticationResponse authenticate(AuthenticationRequest request){
        T user = repository.findByEmail(request.getUsername()).orElseThrow(AccountNotFoundException::new);
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    ));
            String jwtToken = jwtService.generateToken(user);

            return AuthenticationResponse
                    .builder()
                    .token(jwtToken)
                    .role(user.getRole().name())
                    .build();
        }catch (DisabledException e){
            throw new AccountNotEnabledException("Account not enabled- Check the email for confirmation"); //todo  handle error
        }catch (BadCredentialsException e){
            // Create a map to hold your error details

            throw new CustomBadCredentialsException();
        }
    }
}
