package ph.jsalcedo.backendv2.exceptions;

public class EmailErrorException extends RuntimeException {
    public EmailErrorException(String email) { //todo test
        //todo Add to Exception handler
        super(String.format("Email sent to %s is not successful", email));
    }
}
