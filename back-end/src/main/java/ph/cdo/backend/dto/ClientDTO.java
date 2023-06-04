package ph.cdo.backend.dto;

import ph.cdo.backend.enums.Role;

import java.math.BigDecimal;

public record ClientDTO(
        Long id,
        Role role,
        String email,
        String mobilePhone,
        BigDecimal balance

){
}
