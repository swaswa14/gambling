package ph.cdo.backend.response;

import lombok.Builder;
import lombok.Data;
import ph.cdo.backend.enums.Role;

@Data
@Builder
public class AuthenticationResponse {
    private String token;
    private Role role;
}
