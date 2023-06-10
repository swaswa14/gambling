package ph.cdo.backend.dto.mapper.impl;

import org.springframework.stereotype.Service;
import ph.cdo.backend.dto.records.FieldErrorDTO;
import ph.cdo.backend.exceptions.CustomFieldErrorException;
import ph.cdo.backend.exceptions.SpecificFieldError;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service

public class FieldErrorDTOMapper implements Function<CustomFieldErrorException, FieldErrorDTO> {

    @Override
    public FieldErrorDTO apply(CustomFieldErrorException customFieldErrorException) {

        Map<String, SpecificFieldError> objectMap = new HashMap<>();
        for(SpecificFieldError field : customFieldErrorException.getSpecificFieldErrorList()){
            objectMap.put(field.getFieldName(), field);
        }

        return new FieldErrorDTO(
                customFieldErrorException.getSpecificFieldErrorList().size(),
                objectMap,
                customFieldErrorException.getHttpStatus(),
                customFieldErrorException.getException().getSimpleName()
        );
    }
}
