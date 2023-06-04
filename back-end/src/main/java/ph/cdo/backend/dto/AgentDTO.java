package ph.cdo.backend.dto;

import ph.cdo.backend.enums.Role;

public record AgentDTO(
        Long id,
        Role role,
        String email,
        String mobilePhone,
        String agentCode
) {
}
