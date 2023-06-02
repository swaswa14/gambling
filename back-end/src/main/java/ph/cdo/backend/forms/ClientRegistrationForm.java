package ph.cdo.backend.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientRegistrationForm {

    @NotNull
    @NotBlank
    private String name;
    @Email(message = "Must be a valid email")
    private String email;

    @NotBlank
    @NotNull
    @NotEmpty
    private String password;

    private String invitationCode;


    private String mobilePhone;
}
