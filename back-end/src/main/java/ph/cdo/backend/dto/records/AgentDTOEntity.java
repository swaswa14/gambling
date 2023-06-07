package ph.cdo.backend.dto.records;

import ph.cdo.backend.dto.DTOEntity;
import ph.cdo.backend.enums.Role;

public record AgentDTOEntity(
        Long id,
        Role role,
        String email,
        String mobilePhone,
        String agentCode
) implements DTOEntity {
    public AgentDTOEntity(Long id, Role role, String email, String mobilePhone, String agentCode) {
        this.id = id;
        this.role = role;
        this.email = email;
        this.mobilePhone = mobilePhone;
        this.agentCode = agentCode;
    }


    @Override
    public Long getID() {
        return id;
    }


}
