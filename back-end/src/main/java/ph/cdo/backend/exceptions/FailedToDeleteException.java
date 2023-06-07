package ph.cdo.backend.exceptions;

public class FailedToDeleteException extends RuntimeException{
    public FailedToDeleteException(String entityName, Long id) { //todo needs testing!!!
        super(String.format("Cannot delete %s with id %d: ", entityName, id));
    }
}
