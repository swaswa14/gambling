package ph.cdo.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ph.cdo.backend.dto.records.AdminDTOEntity;
import ph.cdo.backend.dto.mapper.impl.AdminDTOMapper;
import ph.cdo.backend.entity.user.Admin;
import ph.cdo.backend.repository.AdminRepository;
import ph.cdo.backend.service.AdminService;

@Service("AdminService")
public class AdminServiceImpl extends IUserServiceImpl<Admin, AdminDTOEntity, AdminDTOMapper> implements AdminService {



    public AdminServiceImpl(
            @Autowired @Qualifier("AdminRepository") AdminRepository adminRepository,
            @Autowired @Qualifier("AdminDTOMapper") AdminDTOMapper userDTOMapper)
    {
        super(userDTOMapper, adminRepository);
    }
}
