package ph.cdo.backend.exceptions;

public class    DuplicateEmailException extends RuntimeException{
    public DuplicateEmailException(String message) {
        super(String.format("email with %s already exists!", message.toLowerCase()));
    }
}
