package ph.cdo.backend.entity.wallet;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public abstract class Wallet {

    protected BigDecimal balance;

    public Wallet(BigDecimal balance) {
        this.balance = balance;
    }

    protected String formattedBalance;



}


