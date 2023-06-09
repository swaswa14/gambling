package ph.cdo.backend.request;

import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ph.cdo.backend.exceptions.validtion_group.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@GroupSequence({Group1.class, Group2.class, Group3.class, Group4.class, Group5.class, ClientRegistrationRequest.class})
public class ClientRegistrationRequest {

    @NotNull(message = "Email is required", groups = Group1.class )
    @NotBlank(message = "Entered email is blank", groups = Group2.class)
    @Email(message = "Must be a valid email", groups = Group3.class)
    private String email;


    @NotNull(message = "Password is required", groups = Group1.class)
    @NotBlank(message = "Entered password is blank", groups = Group2.class)

    @Size(min = 6, max = 255, message = "Password length should be between 6 and 255 characters", groups = Group3.class)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\\$%\\^&\\*]).{6,255}$",
            message = "Password must contain at least 1 digit, 1 lowercase letter, 1 uppercase letter, and 1 special character", groups = Group4.class)
    private String password;

    @Pattern(regexp = "\\d{4,6}", message = "Invitation code should be between 4 and 6 characters and must only contain a number", groups = Group5.class)

    private String invitationCode;


    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$", message = "Phone number is not a valid number", groups = Group5.class)
    private String mobilePhone;
}
