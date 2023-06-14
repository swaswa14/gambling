package ph.jsalcedo.backendv2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FailedToDeleteException extends RuntimeException{
    public FailedToDeleteException(String entityName, Long id) { //todo needs testing!!!
        super(String.format("Cannot delete %s with id %d: ", entityName, id));
    }
}
