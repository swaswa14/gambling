package ph.cdo.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ph.cdo.backend.dto.mapper.impl.user.TokenDTOMapper;
import ph.cdo.backend.dto.records.TokenDTO;
import ph.cdo.backend.entity.Token;
import ph.cdo.backend.entity.base.User;
import ph.cdo.backend.enums.TokenType;
import ph.cdo.backend.exceptions.EntityDoesNotExistsException;
import ph.cdo.backend.repository.TokenRepository;
import ph.cdo.backend.service.TokenService;
import ph.cdo.backend.service.impl.base_entity.BaseEntityServiceImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TokenServiceImpl extends BaseEntityServiceImpl<Token, TokenDTO, TokenDTOMapper> implements TokenService {
    private final TokenRepository repository;

    @Autowired
    public TokenServiceImpl(
            @Qualifier("TokenRepository") TokenRepository repository,
            @Qualifier("TokenDTOMapper")TokenDTOMapper mapper)

    {
        super(repository, mapper);
        this.repository = repository;
    }

    @Override
    public Date generateExpiryDate(int days, int months, int years) {
        Calendar calendar = Calendar.getInstance();

        // Add the specified days, months, and years to the current date
        calendar.add(Calendar.DAY_OF_MONTH, days);
        calendar.add(Calendar.MONTH, months);
        calendar.add(Calendar.YEAR, years);

        // Get the calculated date

        return calendar.getTime();
    }

    @Override
    public List<TokenDTO> findByTokenType(TokenType tokenType) {
        return null;
    }

    @Override
    public Token findByUser(User user) {
        return null;
    }

    @Override
    public void sendEnableAccount(User savedUser) {
        TokenDTO token = tokenBuilder(savedUser, TokenType.Enabled_Account, 1,0,0);

    }

    @Override
    public TokenDTO tokenBuilder(User user,TokenType tokenType, int days, int month, int years){
        return super.create(Token.builder()
                .expiryDate(generateExpiryDate(days,month,years))
                .tokenType(tokenType)
                .user(user)
                .build());
    }

    @Override
    public Token findByToken(String token) {
       return  repository.findFirstByToken(token).orElseThrow(() -> new EntityDoesNotExistsException(token));

    }
}
