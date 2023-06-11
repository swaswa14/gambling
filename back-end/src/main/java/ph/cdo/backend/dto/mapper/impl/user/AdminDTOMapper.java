package ph.cdo.backend.dto.mapper.impl.user;

import org.springframework.stereotype.Service;
import ph.cdo.backend.dto.records.AdminDTOEntity;
import ph.cdo.backend.entity.user.Admin;

@Service("AdminDTOMapper")
public class AdminDTOMapper extends UserDTOMapper<Admin, AdminDTOEntity> {
    @Override
    public AdminDTOEntity apply(Admin admin) {
        return new AdminDTOEntity(
                admin.getId(),
                admin.getRole(),
                admin.getEmail(),
                admin.getMobilePhone(),
                admin.getAgentCode(),
                admin.getName()
        );

    }
}
