package ph.cdo.backend.exceptions;

public class EmptyListException extends RuntimeException{
    public EmptyListException(String list) {
        super(String.format("The %s list is empty", list));
    }
}
