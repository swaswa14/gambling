package ph.cdo.backend.forms;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginForm {

    private String email;
    private String password;
}
