package ph.cdo.backend.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientRegistrationResponse {
    String header;
    String body;
    String email;
    String footer;
}
