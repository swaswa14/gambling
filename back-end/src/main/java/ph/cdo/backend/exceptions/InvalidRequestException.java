package ph.cdo.backend.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
@Getter
public class InvalidRequestException extends RuntimeException {
    private List<ApiError> errors;

    public InvalidRequestException(String message, List<ApiError> errors) {
        super(message);
        this.errors = errors;

    }


}
