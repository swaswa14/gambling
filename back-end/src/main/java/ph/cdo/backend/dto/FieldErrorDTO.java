package ph.cdo.backend.dto;

import org.springframework.http.HttpStatus;
import ph.cdo.backend.errors.SpecificFieldError;

import java.util.Map;

public record FieldErrorDTO(
        int numberOfErrors,
        Map<String, SpecificFieldError> errorMaps,
        HttpStatus status,
        String exception

) {
}
