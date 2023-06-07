package ph.cdo.backend.exceptions;

public class EntityDoesNotExistsException extends RuntimeException{
    public EntityDoesNotExistsException(Long id) {
        super(String.format("%d does not exists", id));
    }
}
