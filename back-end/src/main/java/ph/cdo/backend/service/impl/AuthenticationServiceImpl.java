package ph.cdo.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ph.cdo.backend.dto.ClientDTO;
import ph.cdo.backend.entity.Transaction;
import ph.cdo.backend.entity.user.Admin;
import ph.cdo.backend.entity.user.Agent;
import ph.cdo.backend.entity.user.Client;
import ph.cdo.backend.entity.user.User;
import ph.cdo.backend.enums.Role;
import ph.cdo.backend.errors.DuplicateEmailException;
import ph.cdo.backend.errors.UserRegistrationErrorException;
import ph.cdo.backend.repository.AdminRepository;
import ph.cdo.backend.repository.AgentRepository;
import ph.cdo.backend.repository.ClientRepository;
import ph.cdo.backend.request.AuthenticationRequest;
import ph.cdo.backend.request.ClientRegistrationRequest;
import ph.cdo.backend.response.AuthenticationResponse;
import ph.cdo.backend.response.ClientRegistrationResponse;
import ph.cdo.backend.service.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

@Service

public class AuthenticationServiceImpl implements AuthenticationService {


    @Qualifier("ClientRepository")
    private final ClientRepository clientRepository;

    @Qualifier("AdminRepository")
    private final AdminRepository adminRepository;

    @Qualifier("AgentRepository")
    private final AgentRepository agentRepository;

    @Qualifier("ClientService")
    private final ClientService clientService;
    @Qualifier("AdminService")
    private final AdminService adminService;

    @Qualifier("AgentService")
    private final AgentService agentService;

    private final JwtService jwtService;




    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationServiceImpl(ClientRepository clientRepository, AdminRepository adminRepository, AgentRepository agentRepository, ClientService clientService, AdminService adminService, AgentService agentService, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.clientRepository = clientRepository;
        this.adminRepository = adminRepository;
        this.agentRepository = agentRepository;
        this.clientService = clientService;
        this.adminService = adminService;
        this.agentService = agentService;
        this.jwtService = jwtService;

        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public ClientRegistrationResponse registerClient(ClientRegistrationRequest request) {

        if(clientService.isEmailTaken(request.getEmail())||
            adminService.isEmailTaken(request.getEmail())||
                agentService.isEmailTaken(request.getEmail())
        ){
            throw new DuplicateEmailException(request.getEmail());
        }



        Client client = Client.builder()
                .email(request.getEmail().toLowerCase())
                .password(passwordEncoder.encode(request.getPassword()))
                .invitationCode(request.getInvitationCode())
                .mobilePhone(request.getMobilePhone())
                .isEnabled(false)
                .isLocked(false)
                .role(Role.Client)
                .balance(BigDecimal.valueOf(0.0))
                .transactions(new ArrayList<Transaction>())
                .build();

        ClientDTO registeredClient = clientService.save(client);

        Optional<Client> optionalClient = clientRepository.findById(registeredClient.id());

        if(optionalClient.isPresent()){
            Client authenticatedClient = optionalClient.get();

            String jwtToken = jwtService.generateToken(authenticatedClient);

            return ClientRegistrationResponse.builder()
                    .header("Registration Successful")
                    .body("Please confirm your email to activate your account!")
                    .footer("Email will expired in 24 hours")
                    .email(authenticatedClient.getEmail())
                    .token(jwtToken)
                    .build();
        }
        else throw new UserRegistrationErrorException();


    }



    public AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception {

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    ));


            Optional<Client> optionalClient = clientRepository.findAllByEmail(request.getUsername()).stream().findFirst();
            Optional<Admin> optionalAdmin =  adminRepository.findAllByEmail(request.getUsername()).stream().findFirst();
            Optional<Agent> optionalAgent = agentRepository.findAllByEmail(request.getUsername()).stream().findFirst();


            User user = null;

            if(optionalClient.isPresent()){
                user = optionalClient.get();

            }else if(optionalAdmin.isPresent()) {
                user = optionalAdmin.get();
            }else if(optionalAgent.isPresent()) {
                user = optionalAgent.get();
            } else{
                throw new BadCredentialsException("INVALID_CREDENTIALS");
            }



            String jwtToken = jwtService.generateToken(user);

            return AuthenticationResponse
                    .builder()
                    .token(jwtToken)
                    .role(user.getRole())
                    .build();
        }catch (DisabledException e){
            throw new DisabledException("USER_DISABLED");
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("INVALID_CREDENTIALS");
        }


    }




}
