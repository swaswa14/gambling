package ph.cdo.backend.dto.mapper;

import org.springframework.stereotype.Service;
import ph.cdo.backend.dto.FieldErrorDTO;
import ph.cdo.backend.errors.CustomFieldError;
import ph.cdo.backend.errors.SpecificFieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service

public class FieldErrorDTOMapper implements Function<CustomFieldError, FieldErrorDTO> {

    @Override
    public FieldErrorDTO apply(CustomFieldError customFieldError) {

        Map<String, SpecificFieldError> objectMap = new HashMap<>();
        for(SpecificFieldError field : customFieldError.getSpecificFieldErrorList()){
            objectMap.put(field.getStatusCode().toUpperCase() + " ERROR", field);
        }

        return new FieldErrorDTO(
                customFieldError.getSpecificFieldErrorList().size(),
                objectMap,
                customFieldError.getHttpStatus(),
                customFieldError.getException().getSimpleName()
        );
    }
}
