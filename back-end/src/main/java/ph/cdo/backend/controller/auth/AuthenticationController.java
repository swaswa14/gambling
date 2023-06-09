package ph.cdo.backend.controller.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ph.cdo.backend.dto.records.FieldErrorDTO;
import ph.cdo.backend.dto.mapper.impl.FieldErrorDTOMapper;
import ph.cdo.backend.exceptions.*;
import ph.cdo.backend.request.AuthenticationRequest;
import ph.cdo.backend.request.ClientRegistrationRequest;
import ph.cdo.backend.response.AuthenticationResponse;
import ph.cdo.backend.response.ClientRegistrationResponse;
import ph.cdo.backend.service.AuthenticationService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthenticationController {


    private final AuthenticationService authenticationService;
    private final FieldErrorDTOMapper fieldErrorDTOMapper;

    @PostMapping(value= "/register/client", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)

    public ResponseEntity<ClientRegistrationResponse> registerClient(
            @RequestBody @Valid @Validated ClientRegistrationRequest request ,
            BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            FieldErrorDTO fieldErrorDTO = fieldErrorDTOFunction(bindingResult);

            throw new ValidationFieldException("Field are invalid!", fieldErrorDTO);

        }else {
            ClientRegistrationResponse response = authenticationService.registerClient(request);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
    }


            @PostMapping(value = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
            @ResponseStatus(HttpStatus.OK)
            public ResponseEntity<AuthenticationResponse> authenticate( @Valid @RequestBody AuthenticationRequest request, BindingResult bindingResult) throws JsonProcessingException {


                if(bindingResult.hasErrors()){
                    FieldErrorDTO fieldErrorDTO = fieldErrorDTOFunction(bindingResult);

                    throw new ValidationFieldException("Field are invalid!", fieldErrorDTO);

                }else {
                    AuthenticationResponse authenticationResponse = authenticationService.authenticate(request);

        //            HttpHeaders headers = new HttpHeaders();
        //            String tokenCookie = String.format("token=%s; Max-Age=3600; Secure; HttpOnly", authenticationResponse.getToken());
        //            String roleCookie = String.format("role=%s; Max-Age=3600; Secure; HttpOnly", authenticationResponse.getRole());
        //
        //            headers.add(HttpHeaders.SET_COOKIE, tokenCookie);
        //            headers.add(HttpHeaders.SET_COOKIE, roleCookie);

                    return ResponseEntity.ok().body(authenticationResponse);
                }
            }


    private FieldErrorDTO fieldErrorDTOFunction(BindingResult bindingResult){
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

        return fieldErrorDTOMapper.apply(
                CustomFieldErrorException.builder()
                        .specificFieldErrorList(specificFieldErrorList)
                        .httpStatus(HttpStatus.UNPROCESSABLE_ENTITY)
                        .exception(InvalidValueException.class)
                        .build());

    }
}
