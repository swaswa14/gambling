package ph.cdo.backend.exceptions;

import java.util.Arrays;

public class InvalidValueException extends RuntimeException{
    public <T> InvalidValueException(Object ... object) {
        super(String.format("Invalid Value%s: %s",  object.length >= 2 ? "s" : "", Arrays.toString(object)));
    }
}
