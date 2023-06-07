package ph.cdo.backend.service;

import ph.cdo.backend.dto.records.TokenDTO;
import ph.cdo.backend.entity.Token;
import ph.cdo.backend.entity.base.User;
import ph.cdo.backend.enums.TokenType;

import java.util.Date;
import java.util.List;

public interface TokenService extends BaseEntityService<Token, TokenDTO>{

    Date generateExpiryDate(int days, int months, int years);

    List<TokenDTO> findByTokenType(TokenType tokenType);

    Token findByUser(User user);
}
