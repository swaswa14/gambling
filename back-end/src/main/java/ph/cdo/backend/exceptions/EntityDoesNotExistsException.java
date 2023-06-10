package ph.cdo.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityDoesNotExistsException extends RuntimeException{
    public EntityDoesNotExistsException(Object id) {
        super(String.format("%d does not exists", id));
    }
}
