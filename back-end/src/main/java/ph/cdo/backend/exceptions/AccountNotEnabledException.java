package ph.cdo.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.FORBIDDEN)
public class AccountNotEnabledException extends DisabledException {
    public AccountNotEnabledException(String msg) {
        super(msg);
    }
}
