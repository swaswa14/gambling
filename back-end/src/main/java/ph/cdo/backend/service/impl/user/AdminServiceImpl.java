package ph.cdo.backend.service.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ph.cdo.backend.dto.records.AdminDTOEntity;
import ph.cdo.backend.dto.mapper.impl.user.AdminDTOMapper;
import ph.cdo.backend.entity.user.Admin;
import ph.cdo.backend.repository.AdminRepository;
import ph.cdo.backend.service.AdminService;
import ph.cdo.backend.service.EmailService;
import ph.cdo.backend.service.impl.base_entity.IUserServiceImpl;

@Service("AdminService")
public class AdminServiceImpl extends IUserServiceImpl<Admin, AdminDTOEntity, AdminDTOMapper> implements AdminService {

    private final EmailService emailService;


    public AdminServiceImpl(
            @Autowired @Qualifier("AdminRepository") AdminRepository adminRepository,
            @Autowired @Qualifier("AdminDTOMapper") AdminDTOMapper userDTOMapper,
            @Autowired EmailService emailService)
    {
        super(userDTOMapper, adminRepository, emailService);
        this.emailService = emailService;
    }
}
