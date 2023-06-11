package ph.cdo.backend.service.impl.authentication;

import ph.cdo.backend.request.AuthenticationRequest;
import ph.cdo.backend.request.registration.BasicRegistrationForm;
import ph.cdo.backend.response.AuthenticationResponse;
import ph.cdo.backend.response.UserRegistrationResponse;

public interface AuthenticationService<T extends BasicRegistrationForm> {
    UserRegistrationResponse registerClient(T request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
