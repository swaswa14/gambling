package ph.cdo.backend.dto.mapper;

import ph.cdo.backend.dto.AdminDTO;
import ph.cdo.backend.entity.user.Admin;

import java.util.function.Function;

public class AdminDTOMapper extends UserDTOMapper implements Function<Admin, AdminDTO> {
    @Override
    public AdminDTO apply(Admin admin) {
        return new AdminDTO(
                admin.getId(),
                admin.getRole(),
                admin.getEmail(),
                admin.getMobilePhone(),
                admin.getName()
        );

    }
}
