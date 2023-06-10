package ph.cdo.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException() {
        super("Email not found or does not exist as an account");
    }
}
