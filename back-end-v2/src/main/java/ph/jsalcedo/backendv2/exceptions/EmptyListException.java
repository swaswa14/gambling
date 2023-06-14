package ph.jsalcedo.backendv2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmptyListException extends RuntimeException{
    public EmptyListException(String list) {
        super(String.format("The %s list is empty", list));
    }
}
