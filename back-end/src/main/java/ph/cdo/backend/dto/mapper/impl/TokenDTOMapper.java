package ph.cdo.backend.dto.mapper.impl;

import org.springframework.stereotype.Service;
import ph.cdo.backend.dto.records.TokenDTO;
import ph.cdo.backend.dto.mapper.BaseEntityDTOMapper;
import ph.cdo.backend.entity.Token;


//todo create test!
@Service("TokenDTOMapper")
public class TokenDTOMapper extends BaseEntityDTOMapper<Token, TokenDTO> {
    @Override
    public TokenDTO apply(Token token) {
        return new TokenDTO(
                token.getId(),
                token.getUser().getId(),
                token.getUser().getEmail(),
                token.getToken(),
                token.getTokenType(),
                token.getExpiryDate()
        );
    }
}
