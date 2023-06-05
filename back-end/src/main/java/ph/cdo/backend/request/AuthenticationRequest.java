package ph.cdo.backend.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationRequest {
    @NotBlank(message = "USERNAME IS BLANK")
    private String username;

    @NotBlank(message = "PASSWORD IS BLANK")
    String password;
}
