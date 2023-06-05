package ph.cdo.backend.errors;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;


@Data
@Getter
@Builder
public class CustomFieldError {
    private HttpStatus httpStatus;
    private List<SpecificFieldError> specificFieldErrorList;
    private Class<?> exception;
}
