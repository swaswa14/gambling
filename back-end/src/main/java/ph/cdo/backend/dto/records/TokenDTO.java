package ph.cdo.backend.dto.records;

import ph.cdo.backend.dto.DTOEntity;
import ph.cdo.backend.enums.TokenType;

import java.util.Date;

public record TokenDTO(
        Long id,
        Long userID,
        String userEmail,
        String token,
        TokenType tokenType,
        Date expiryDate

) implements DTOEntity {
    @Override
    public Long getID() {
        return id;
    }
}
