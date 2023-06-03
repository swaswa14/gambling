package ph.cdo.backend.errors;

import java.util.Arrays;

public class InvalidValueException extends RuntimeException{
    public <T> InvalidValueException(Object ... object) {
        super(String.format("Invalid Value%s : %s", Arrays.toString(object), object.length >= 2 ? "s" : ""));
    }
}
