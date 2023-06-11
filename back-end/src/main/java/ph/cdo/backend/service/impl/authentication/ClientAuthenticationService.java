package ph.cdo.backend.service.impl.authentication;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ph.cdo.backend.dto.mapper.impl.registration.ClientRegistrationMapper;
import ph.cdo.backend.dto.records.ClientDTOEntity;
import ph.cdo.backend.entity.user.Client;
import ph.cdo.backend.repository.ClientRepository;

import ph.cdo.backend.request.registration.ClientBasicRegistrationForm;
import ph.cdo.backend.service.EmailService;
import ph.cdo.backend.service.impl.JwtService;
import ph.cdo.backend.service.impl.user.ClientService;

@Service("ClientAuthenticationService")
public class ClientAuthenticationService extends AuthenticationServiceImpl<
        Client,
        ClientDTOEntity,
        ClientService,
        ClientBasicRegistrationForm,
        ClientRegistrationMapper,
        ClientRepository
        > {
    @Autowired
    public ClientAuthenticationService(@Qualifier("ClientRepository") ClientRepository repository,
                                       @Qualifier("ClientService")ClientService service,
                                       JwtService jwtService, EmailService emailService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
                                       @Qualifier("ClientRegistrationMapper")ClientRegistrationMapper mapper) {
        super(repository, service, jwtService, emailService, passwordEncoder, authenticationManager, mapper);
    }
}
