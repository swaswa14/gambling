package ph.cdo.backend.controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ph.cdo.backend.errors.ApiError;
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

//todo test this!!!!
    @PostMapping(value= "/register/client")
    public ResponseEntity<Object> registerClient(
            @RequestBody ClientRegistrationRequest request ,
            BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
            Map<String, ApiError> errorMap = new HashMap<>();

            for(FieldError t : fieldErrorList) {
                errorMap.put(t.getField(),
                        ApiError.builder()
                                .message(t.getDefaultMessage())
                                .timeStamp(ZonedDateTime.now(ZoneId.of("Z")))
                                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                                .statusCode(Integer.toString(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                                .build());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
        }

        ClientRegistrationResponse response = authenticationService.registerClient(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
}
