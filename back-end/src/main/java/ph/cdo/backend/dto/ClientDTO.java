package ph.cdo.backend.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import ph.cdo.backend.entity.user.Client;
import ph.cdo.backend.entity.user.User;
import ph.cdo.backend.enums.Role;

import java.math.BigDecimal;

public record ClientDTO(
        Long id,
        @Enumerated(EnumType.STRING)
        Role role,
        String email,
        String mobilePhone,
        BigDecimal balance

) implements DTOEntity{




    public ClientDTO(Long id, Role role, String email, String mobilePhone, BigDecimal balance) {
        this.id = id;
        this.role = role;
        this.email = email;
        this.mobilePhone = mobilePhone;
        this.balance = balance;
    }





}
