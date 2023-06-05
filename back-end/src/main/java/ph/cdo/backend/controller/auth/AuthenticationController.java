package ph.cdo.backend.controller.auth;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ph.cdo.backend.dto.FieldErrorDTO;
import ph.cdo.backend.dto.mapper.FieldErrorDTOMapper;
import ph.cdo.backend.errors.*;
import ph.cdo.backend.request.ClientRegistrationRequest;
import ph.cdo.backend.response.ClientRegistrationResponse;
import ph.cdo.backend.service.AuthenticationService;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {


    private final AuthenticationService authenticationService;
    private final FieldErrorDTOMapper fieldErrorDTOMapper;
//todo test this!!!!
    @PostMapping(value= "/register/client", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ClientRegistrationResponse> registerClient(
            @RequestBody @Valid @Validated ClientRegistrationRequest request ,
            BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
            List<SpecificFieldError> specificFieldErrorList = fieldErrorList
                    .stream()
                    .map(t-> SpecificFieldError
                            .builder()
                            .exceptionName(t.getClass().getSimpleName())
                            .fieldName(t.getField())
                            .rejectedValue(t.getRejectedValue())
                            .errorMessage(t.getDefaultMessage())
                            .statusCode(t.getCode())
                            .build()
                    )
                    .toList();

            FieldErrorDTO fieldErrorDTO = fieldErrorDTOMapper.apply(
                    CustomFieldError.builder()
                            .specificFieldErrorList(specificFieldErrorList)
                            .httpStatus(HttpStatus.UNPROCESSABLE_ENTITY)
                            .exception(InvalidValueException.class)
                            .build());

            throw new ValidationFieldException("Field are invalid!", fieldErrorDTO);

        }else {
            ClientRegistrationResponse response = authenticationService.registerClient(request);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }



    }
}
