package ph.cdo.backend.service.impl.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ph.cdo.backend.dto.mapper.impl.registration.AgentRegistrationMapper;
import ph.cdo.backend.dto.records.AgentDTOEntity;
import ph.cdo.backend.entity.user.Agent;
import ph.cdo.backend.repository.AgentRepository;
import ph.cdo.backend.request.registration.AgentBasicRegistrationForm;
import ph.cdo.backend.service.AgentService;
import ph.cdo.backend.service.EmailService;
import ph.cdo.backend.service.impl.JwtService;

@Service("AgentAuthenticationService")
public class AgentAuthenticationService extends AuthenticationServiceImpl<
        Agent,
        AgentDTOEntity,
        AgentService,
        AgentBasicRegistrationForm,
        AgentRegistrationMapper,
        AgentRepository
        >{
    @Autowired
    public AgentAuthenticationService(@Qualifier("AgentRepository") AgentRepository repository,
                                      @Qualifier("AgentService") AgentService service,
                                      JwtService jwtService,
                                      EmailService emailService,
                                      PasswordEncoder passwordEncoder,
                                      AuthenticationManager authenticationManager,
                                      @Qualifier("AgentRegistrationMapper") AgentRegistrationMapper mapper) {
        super(repository, service, jwtService, emailService, passwordEncoder, authenticationManager, mapper);
    }
}
