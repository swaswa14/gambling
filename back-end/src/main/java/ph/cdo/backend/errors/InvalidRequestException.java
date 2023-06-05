package ph.cdo.backend.errors;

import java.util.Map;

public class InvalidRequestException extends RuntimeException {
    private Map<String, ApiError> errors;

    public InvalidRequestException(String message, Map<String, ApiError> errors) {
        super(message);
        this.errors = errors;
    }

    public Map<String, ApiError> getErrors() {
        return errors;
    }
}
