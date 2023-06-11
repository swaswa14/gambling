package ph.cdo.backend.request.registration;

import jakarta.persistence.Embedded;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder
//@GroupSequence({Group4.class, Group3.class, Group1.class, Group2.class, Group5.class,
//        Group6.class,
//        Group7.class,
//        Group8.class,
//        ClientBasicRegistrationForm.class})
public class ClientBasicRegistrationForm extends BasicRegistrationForm {


    @Pattern(regexp = "\\d{4,6}", message = "Invitation code should be between 4 and 6 characters and must only contain a number")

    private String invitationCode;




}
