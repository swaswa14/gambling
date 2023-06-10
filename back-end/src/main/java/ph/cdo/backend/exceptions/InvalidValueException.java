package ph.cdo.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidValueException extends RuntimeException{
    public  InvalidValueException(Object ... object) {
        super(String.format("Invalid Value%s: %s",  object.length >= 2 ? "s" : "", Arrays.toString(object)));
    }
}
