package ph.cdo.backend.exceptions;

import ph.cdo.backend.entity.Token;

public class TokenExpiredException extends RuntimeException{
    public TokenExpiredException(Token token) {
        super(String.format("Token is already expired! \ncreationDate: %s\nexpirationDate%s", token.getCreatedOn().toString(), token.getExpiryDate().toString()));
    }
}
