package ph.cdo.backend.errors;

public class EmptyListException extends RuntimeException{
    public EmptyListException(String list) {
        super(String.format("The %s list is empty", list));
    }
}
