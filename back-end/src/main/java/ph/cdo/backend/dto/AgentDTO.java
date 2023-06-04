package ph.cdo.backend.dto;

import ph.cdo.backend.entity.user.Agent;
import ph.cdo.backend.enums.Role;

public record AgentDTO(
        Long id,
        Role role,
        String email,
        String mobilePhone,
        String agentCode
) implements DTOEntity{
    public AgentDTO(Long id, Role role, String email, String mobilePhone, String agentCode) {
        this.id = id;
        this.role = role;
        this.email = email;
        this.mobilePhone = mobilePhone;
        this.agentCode = agentCode;
    }


}
