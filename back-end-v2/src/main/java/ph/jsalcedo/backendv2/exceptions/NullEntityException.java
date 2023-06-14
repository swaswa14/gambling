package ph.jsalcedo.backendv2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NullEntityException extends RuntimeException {
    public  NullEntityException() {
        super("Cannot save a Null from Entity");
    }
}
