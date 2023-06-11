package ph.cdo.backend.dto.records;

import ph.cdo.backend.dto.DTOEntity;
import ph.cdo.backend.entity.base.Name;
import ph.cdo.backend.enums.Role;

public record AdminDTOEntity(
        Long id,
        Role role,
        String email,
        String mobilePhone,
        String agentCode,
        Name name
) implements DTOEntity {


    @Override
    public Long getID() {
        return id;
    }


}
