package ph.jsalcedo.backendv2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class  DuplicateEmailException extends RuntimeException{
    public DuplicateEmailException(String message) {
        super(String.format("email with %s already exists!", message.toLowerCase()));
    }
}
