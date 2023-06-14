package ph.jsalcedo.backendv2.dto.user.details;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ph.jsalcedo.backendv2.dto.BaseDto;

import java.util.Date;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AbstractUserDetailsDto implements BaseDto {

    private Long id;
    private String email;
    private String firstname;
    private String lastname;
    private String phone;
    private Date birthday;


}
