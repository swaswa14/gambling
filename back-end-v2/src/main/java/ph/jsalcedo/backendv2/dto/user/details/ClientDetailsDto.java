package ph.jsalcedo.backendv2.dto.user.details;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public final class ClientDetailsDto extends AbstractUserDetailsDto{
    private BigDecimal balance;
}
