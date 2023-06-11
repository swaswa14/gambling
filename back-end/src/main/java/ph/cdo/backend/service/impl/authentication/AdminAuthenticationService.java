package ph.cdo.backend.service.impl.authentication;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ph.cdo.backend.dto.mapper.impl.registration.AdminRegistrationMapper;
import ph.cdo.backend.dto.records.AdminDTOEntity;
import ph.cdo.backend.entity.user.Admin;
import ph.cdo.backend.repository.AdminRepository;

import ph.cdo.backend.request.registration.AdminBasicRegistrationForm;
import ph.cdo.backend.service.AdminService;
import ph.cdo.backend.service.EmailService;
import ph.cdo.backend.service.impl.JwtService;

@Service("AdminAuthenticationService")
public class AdminAuthenticationService extends AuthenticationServiceImpl<
        Admin,
        AdminDTOEntity,
        AdminService,
        AdminBasicRegistrationForm,
        AdminRegistrationMapper,
        AdminRepository
        >{
    @Autowired
    public AdminAuthenticationService(@Qualifier("AdminRepository") AdminRepository repository,
                                      @Qualifier("AdminService")AdminService service,
                                      JwtService jwtService, EmailService emailService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
                                      @Qualifier("AdminRegistrationMapper") AdminRegistrationMapper mapper) {
        super(repository, service, jwtService, emailService, passwordEncoder, authenticationManager, mapper);
    }
}
