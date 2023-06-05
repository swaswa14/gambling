package ph.cdo.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import ph.cdo.backend.entity.user.User;
import ph.cdo.backend.request.AuthenticationRequest;
import ph.cdo.backend.request.ClientRegistrationRequest;
import ph.cdo.backend.response.AuthenticationResponse;
import ph.cdo.backend.response.ClientRegistrationResponse;

public interface AuthenticationService {
    ClientRegistrationResponse registerClient(ClientRegistrationRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request) throws JsonProcessingException;
}
