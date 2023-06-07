package ph.cdo.backend.exceptions;

public class UserRegistrationErrorException extends RuntimeException{
    public UserRegistrationErrorException() {
        super("There was an error encountered during the registration process");
    }
}
