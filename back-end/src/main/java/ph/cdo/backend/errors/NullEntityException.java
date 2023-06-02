package ph.cdo.backend.errors;

public class NullEntityException extends RuntimeException {
    public  NullEntityException() {
        super("Cannot save a Null from Entity");
    }
}
