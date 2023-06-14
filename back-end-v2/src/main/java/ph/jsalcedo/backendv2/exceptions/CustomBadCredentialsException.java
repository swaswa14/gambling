package ph.jsalcedo.backendv2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class CustomBadCredentialsException extends BadCredentialsException {
    public CustomBadCredentialsException() {
        super("Incorrect username or password");
    }
}
