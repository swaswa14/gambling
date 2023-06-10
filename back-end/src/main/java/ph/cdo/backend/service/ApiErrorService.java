package ph.cdo.backend.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ph.cdo.backend.dto.records.FieldErrorDTO;
import ph.cdo.backend.exceptions.ApiError;


public interface ApiErrorService {

    ApiError apiErrorBuilder(Exception ex, HttpStatus httpStatus);
    FieldErrorDTO fieldErrorDTOFunction(BindingResult bindingResult);
}
