package ph.cdo.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ph.cdo.backend.dto.mapper.impl.user.FieldErrorDTOMapper;
import ph.cdo.backend.dto.records.FieldErrorDTO;
import ph.cdo.backend.exceptions.ApiError;
import ph.cdo.backend.exceptions.CustomFieldErrorException;
import ph.cdo.backend.exceptions.InvalidValueException;
import ph.cdo.backend.exceptions.SpecificFieldError;
import ph.cdo.backend.service.ApiErrorService;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiErrorServiceImpl implements ApiErrorService {
    private final FieldErrorDTOMapper fieldErrorDTOMapper;
    @Override
    public ApiError apiErrorBuilder(Exception ex, HttpStatus httpStatus){
        return ApiError.builder()
                .errorMessage(ex.getMessage())
                .status(httpStatus)
                .statusCode(Integer.toString(httpStatus.value()))
                .timeStamp(ZonedDateTime.now(ZoneId.of("Z")))
                .exception(ex.getClass().getSimpleName())
                .build();
    }

    @Override
    public FieldErrorDTO fieldErrorDTOFunction(BindingResult bindingResult) {
        List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
        List<SpecificFieldError> specificFieldErrorList = fieldErrorList
                .stream()
                .map(t-> SpecificFieldError
                        .builder()
                        .exceptionName(t.getClass().getSimpleName())
                        .fieldName(t.getField())
                        .rejectedValue(t.getRejectedValue())
                        .errorMessage(t.getDefaultMessage())
                        .constraint(t.getCode())
                        .build()
                )
                .toList();

        return fieldErrorDTOMapper.apply(
                CustomFieldErrorException.builder()
                        .specificFieldErrorList(specificFieldErrorList)
                        .httpStatus(HttpStatus.UNPROCESSABLE_ENTITY)
                        .exception(InvalidValueException.class)
                        .build());
    }

}
