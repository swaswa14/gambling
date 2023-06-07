package ph.cdo.backend.dto.records;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import ph.cdo.backend.dto.DTOEntity;
import ph.cdo.backend.enums.Role;

import java.math.BigDecimal;

public record ClientDTOEntity(
        Long id,
        @Enumerated(EnumType.STRING)
        Role role,
        String email,
        String mobilePhone,
        BigDecimal balance

) implements DTOEntity {




    public ClientDTOEntity(Long id, Role role, String email, String mobilePhone, BigDecimal balance) {
        this.id = id;
        this.role = role;
        this.email = email;
        this.mobilePhone = mobilePhone;
        this.balance = balance;
    }


    @Override
    public Long getID() {
        return id;
    }







}
