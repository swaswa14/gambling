package ph.cdo.backend.request;

import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ph.cdo.backend.exceptions.validtion_group.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@GroupSequence({Group1.class, Group2.class, Group3.class,  ClientDepositRequest.class})
public class ClientDepositRequest {
    @NotNull(message = "Must not be blank", groups = Group2.class)
    @Min(value = 1, message = "Minimum deposit amount is 1$", groups = Group1.class)
    private BigDecimal amount;


}
