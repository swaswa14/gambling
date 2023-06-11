package ph.cdo.backend.dto.records;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import ph.cdo.backend.dto.DTOEntity;
import ph.cdo.backend.entity.base.Name;
import ph.cdo.backend.enums.Role;

import java.math.BigDecimal;

public record ClientDTOEntity(
        Long id,
        @Enumerated(EnumType.STRING)
        Role role,
        String email,
        String mobilePhone,
        BigDecimal balance,
        Name name

) implements DTOEntity {







    @Override
    public Long getID() {
        return id;
    }







}
