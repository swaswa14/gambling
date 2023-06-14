package ph.cdo.backend.controller.auth;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ph.cdo.backend.request.AuthenticationRequest;
import ph.cdo.backend.request.registration.AdminBasicRegistrationForm;
import ph.cdo.backend.request.registration.AgentBasicRegistrationForm;
import ph.cdo.backend.request.registration.BasicRegistrationForm;
import ph.cdo.backend.request.registration.ClientBasicRegistrationForm;
import ph.cdo.backend.response.AuthenticationResponse;
import ph.cdo.backend.response.UserRegistrationResponse;
import ph.cdo.backend.service.impl.authentication.AdminAuthenticationService;
import ph.cdo.backend.service.impl.authentication.AgentAuthenticationService;
import ph.cdo.backend.service.impl.authentication.AuthenticationService;
import ph.cdo.backend.service.impl.authentication.ClientAuthenticationService;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/auth")

@CrossOrigin
@Slf4j //Todo make this abstract Authentication Controller!
public class AuthenticationController {


    private Map<String, AuthenticationService> authenticationServiceMap;
    private Map<String, Class<? extends BasicRegistrationForm>> registrationFormMap;


    @Autowired
    public AuthenticationController(@Qualifier("ClientAuthenticationService") ClientAuthenticationService clientAuthenticationService,
                                    @Qualifier("AdminAuthenticationService") AdminAuthenticationService adminAuthenticationService,
                                    @Qualifier("AgentAuthenticationService") AgentAuthenticationService agentAuthenticationService) {
        this.authenticationServiceMap = new HashMap<>();
        this.authenticationServiceMap.put("client", clientAuthenticationService);
        this.authenticationServiceMap.put("admin", adminAuthenticationService);
        this.authenticationServiceMap.put("agent", agentAuthenticationService);

        this.registrationFormMap = new HashMap<>();
        this.registrationFormMap.put("client", ClientBasicRegistrationForm.class);
        this.registrationFormMap.put("admin", AdminBasicRegistrationForm.class);
        this.registrationFormMap.put("agent", AgentBasicRegistrationForm.class);

    }

    @PostMapping(value= "/register/{role}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @SuppressWarnings("unchecked")
    public ResponseEntity<UserRegistrationResponse> registerClient(
            @PathVariable String role,
            @RequestBody @Valid @Validated BasicRegistrationForm request){
            log.info("@Post /api/v1/auth/register/client @RequestBody=  " + request);

            AuthenticationService<BasicRegistrationForm> authenticationService = authenticationServiceMap.get(role.toLowerCase());

        if (authenticationService == null) {
            throw new IllegalArgumentException("Invalid role: " + role);
        }
        Class<? extends BasicRegistrationForm> requiredFormType = registrationFormMap.get(role.toLowerCase());
        if (!requiredFormType.isInstance(request)) {
            throw new IllegalArgumentException("Invalid form type for role: " + role);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.registerClient( request));

    }


            @PostMapping(value = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE)
            @ResponseStatus(HttpStatus.OK)
            public ResponseEntity<AuthenticationResponse> authenticate( @Valid @RequestBody AuthenticationRequest request)  {
                log.info("@Post /api/v1/auth/register/client @RequestBody=  " + request);
        //            HttpHeaders headers = new HttpHeaders();
        //            String tokenCookie = String.format("token=%s; Max-Age=3600; Secure; HttpOnly", authenticationResponse.getToken());
        //            String roleCookie = String.format("role=%s; Max-Age=3600; Secure; HttpOnly", authenticationResponse.getRole());
        //
        //            headers.add(HttpHeaders.SET_COOKIE, tokenCookie);
        //            headers.add(HttpHeaders.SET_COOKIE, roleCookie);

                    return ResponseEntity.ok().body(authenticationServiceMap.get("client").authenticate(request));

            }



}
