package ph.cdo.backend.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class SpecificFieldError {
    private String fieldName;
    private Object rejectedValue;
    private String errorMessage;
    private String exceptionName;
    private String statusCode;
}
