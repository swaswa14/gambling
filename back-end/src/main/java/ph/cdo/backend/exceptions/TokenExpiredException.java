package ph.cdo.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ph.cdo.backend.entity.Token;
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TokenExpiredException extends RuntimeException{
    public TokenExpiredException(Token token) {
        super(String.format("Token is already expired! \ncreationDate: %s\nexpirationDate%s", token.getCreatedOn().toString(), token.getExpiryDate().toString()));
    }
}
