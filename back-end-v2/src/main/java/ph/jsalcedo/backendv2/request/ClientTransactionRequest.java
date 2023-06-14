package ph.jsalcedo.backendv2.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientTransactionRequest {
    @Min(value = 1, message = "Minimum transaction amount is 1$")
    @NotNull(message = "Must not be blank")
    private BigDecimal amount;
}
