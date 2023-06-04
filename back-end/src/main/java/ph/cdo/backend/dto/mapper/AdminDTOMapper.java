package ph.cdo.backend.dto.mapper;

import org.springframework.stereotype.Service;
import ph.cdo.backend.dto.AdminDTO;
import ph.cdo.backend.entity.user.Admin;

import java.util.function.Function;

@Service("AdminDTOMapper")
public class AdminDTOMapper extends UserDTOMapper<Admin, AdminDTO> {
    @Override
    public AdminDTO apply(Admin admin) {
        return new AdminDTO(
                admin.getId(),
                admin.getRole(),
                admin.getEmail(),
                admin.getMobilePhone(),
                admin.getName(),
                admin.getAgentCode()
        );

    }
}
