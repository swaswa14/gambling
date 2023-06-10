package ph.cdo.backend.request;


import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ph.cdo.backend.exceptions.validtion_group.Group4;
import ph.cdo.backend.exceptions.validtion_group.Group3;
import ph.cdo.backend.exceptions.validtion_group.Group1;

import java.math.BigDecimal;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@GroupSequence({Group4.class, Group3.class, Group1.class,  ClientWithdrawalRequest.class})
public class ClientWithdrawalRequest {
    @NotNull(message = "Must not be blank", groups = Group3.class)
    @Min(value = 1, message = "Minimum deposit amount is 1$", groups = Group4.class)
    private BigDecimal amount;
}
