package ph.cdo.backend.errors;

import lombok.Getter;
import ph.cdo.backend.dto.FieldErrorDTO;

@Getter
public class ValidationFieldException extends RuntimeException{
    private final FieldErrorDTO fieldErrorDTO;

    public ValidationFieldException(String message, FieldErrorDTO fieldErrorDTO) {
        super(message);
        this.fieldErrorDTO = fieldErrorDTO;
    }
}
