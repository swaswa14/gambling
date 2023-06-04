package ph.cdo.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ph.cdo.backend.dto.AdminDTO;
import ph.cdo.backend.dto.mapper.AdminDTOMapper;
import ph.cdo.backend.entity.user.Admin;
import ph.cdo.backend.repository.AdminRepository;
import ph.cdo.backend.service.AdminService;

@Service("AdminService")
public class AdminServiceImpl extends IUserServiceImpl<Admin, AdminDTO, AdminDTOMapper> implements AdminService {



    public AdminServiceImpl(
            @Autowired @Qualifier("AdminRepository") AdminRepository adminRepository,
            @Autowired @Qualifier("AdminDTOMapper") AdminDTOMapper userDTOMapper)
    {
        super(userDTOMapper, adminRepository);
    }
}
