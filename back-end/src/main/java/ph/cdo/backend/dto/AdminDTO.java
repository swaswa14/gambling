package ph.cdo.backend.dto;

import ph.cdo.backend.enums.Role;

public record AdminDTO(
        Long id,
        Role role,
        String email,
        String mobilePhone,
        String name
) {
}
