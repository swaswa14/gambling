package ph.cdo.backend.dto.records;

import ph.cdo.backend.dto.DTOEntity;
import ph.cdo.backend.enums.Role;

public record AdminDTOEntity(
        Long id,
        Role role,
        String email,
        String mobilePhone,
        String name,
        String agentCode
) implements DTOEntity {


    @Override
    public Long getID() {
        return id;
    }


}
