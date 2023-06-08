package ph.cdo.backend.repository;

import org.springframework.stereotype.Repository;
import ph.cdo.backend.entity.Token;
import ph.cdo.backend.entity.base.User;
import ph.cdo.backend.enums.TokenType;
import ph.cdo.backend.repository.noBean.BaseEntityRepository;

import java.util.List;
import java.util.Optional;

@Repository("TokenRepository")
//todo test
public interface TokenRepository extends BaseEntityRepository<Token> {
    List<Token> findAllByTokenType(TokenType tokenType);
    Optional<Token> findFirstByUser(User user);

    Optional<Token> findFirstByToken(String token);
}
