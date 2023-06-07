package ph.cdo.backend.dto.records;

import org.springframework.http.HttpStatus;
import ph.cdo.backend.exceptions.SpecificFieldError;

import java.util.Map;

public record FieldErrorDTO(
        int numberOfErrors,
        Map<String, SpecificFieldError> errorMaps,
        HttpStatus status,
        String exception

) {
}
