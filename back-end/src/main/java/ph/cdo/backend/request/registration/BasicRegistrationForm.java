package ph.cdo.backend.request.registration;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class BasicRegistrationForm {
    @Email(message = "Must be a valid email")
    @NotBlank(message = "Email is blank")
    @NotNull(message = "Email is required" )
    protected String email;

    @Pattern(regexp = "[^@ ?!\\,$%]+", message = "Invalid first name")
    @NotNull(message = "First name is required")
    protected String firstName;

    @Pattern(regexp = "[^@ ?!\\,$%]+", message = "Invalid last name")
    @NotNull(message = "Last name is required")
    protected String lastName;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\\$%\\^&\\*]).{6,255}$",
            message = "Password must contain at least 1 digit, 1 lowercase letter, 1 uppercase letter, and 1 special character")
    @Size(min = 6, max = 255, message = "Password length should be between 6 and 255 characters")
    @NotBlank(message = "Password is required")
    protected String password;


    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$", message = "Phone number is not a valid number")
    protected String mobilePhone;


}
