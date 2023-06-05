package ph.cdo.backend.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import ph.cdo.backend.enums.Role;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;


    private String role;



}
