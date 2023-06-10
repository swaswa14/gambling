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
//@GroupSequence({Group4.class, Group3.class, Group1.class, Group2.class, Group5.class,
//        Group6.class,
//        Group7.class,
//        Group8.class,
//        ClientRegistrationRequest.class})
public class ClientRegistrationRequest {



    @Email(message = "Must be a valid email")
    @NotBlank(message = "Email is blank")
    @NotNull(message = "Email is required" )
    private String email;





    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\\$%\\^&\\*]).{6,255}$",
            message = "Password must contain at least 1 digit, 1 lowercase letter, 1 uppercase letter, and 1 special character")
    @Size(min = 6, max = 255, message = "Password length should be between 6 and 255 characters")
    @NotBlank(message = "Password is required")
    private String password;

    @Pattern(regexp = "\\d{4,6}", message = "Invitation code should be between 4 and 6 characters and must only contain a number")

    private String invitationCode;


    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$", message = "Phone number is not a valid number")
    private String mobilePhone;
}
