package ph.cdo.backend.service;

import ph.cdo.backend.entity.user.User;
import ph.cdo.backend.request.ClientRegistrationRequest;
import ph.cdo.backend.response.ClientRegistrationResponse;

public interface AuthenticationService {
    ClientRegistrationResponse registerClient(ClientRegistrationRequest request);
}
