package ph.cdo.backend.controller.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ph.cdo.backend.request.AuthenticationRequest;
import ph.cdo.backend.request.ClientRegistrationRequest;
import ph.cdo.backend.response.AuthenticationResponse;
import ph.cdo.backend.response.ClientRegistrationResponse;
import ph.cdo.backend.service.ApiErrorService;
import ph.cdo.backend.service.AuthenticationService;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthenticationController {


    private final AuthenticationService authenticationService;
    private final ApiErrorService errorService;

    @PostMapping(value= "/register/client", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)

    public ResponseEntity<ClientRegistrationResponse> registerClient(
            @RequestBody @Valid @Validated ClientRegistrationRequest request){

            return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.registerClient(request));

    }


            @PostMapping(value = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE)
            @ResponseStatus(HttpStatus.OK)
            public ResponseEntity<AuthenticationResponse> authenticate( @Valid @RequestBody AuthenticationRequest request)  {
        //            HttpHeaders headers = new HttpHeaders();
        //            String tokenCookie = String.format("token=%s; Max-Age=3600; Secure; HttpOnly", authenticationResponse.getToken());
        //            String roleCookie = String.format("role=%s; Max-Age=3600; Secure; HttpOnly", authenticationResponse.getRole());
        //
        //            headers.add(HttpHeaders.SET_COOKIE, tokenCookie);
        //            headers.add(HttpHeaders.SET_COOKIE, roleCookie);

                    return ResponseEntity.ok().body(authenticationService.authenticate(request));

            }



}
