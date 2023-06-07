package ph.cdo.backend.exceptions;

public class NullEntityException extends RuntimeException {
    public  NullEntityException() {
        super("Cannot save a Null from Entity");
    }
}
