package ph.cdo.backend.dto.mapper.impl.registration;

import org.springframework.stereotype.Service;
import ph.cdo.backend.entity.base.Name;
import ph.cdo.backend.entity.user.Admin;
import ph.cdo.backend.enums.Role;
import ph.cdo.backend.request.registration.AdminBasicRegistrationForm;

@Service("AdminRegistrationMapper")
public class AdminRegistrationMapper extends UserRegistrationMapper<Admin, AdminBasicRegistrationForm> {
    @Override
    public Admin apply(AdminBasicRegistrationForm registrationForm) {
       Admin admin = super.apply(registrationForm);
       admin.setRole(Role.Admin);


       return admin;
    }
}
