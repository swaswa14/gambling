package ph.cdo.backend.entity.wallet;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@Builder(builderMethodName = "childBuilder")
@AllArgsConstructor
public final class Cash extends Wallet {
    private String symbol;


    @Builder(builderMethodName = "childBuilder")
    public Cash(BigDecimal balance, String symbol) {
        super(balance );
        this.symbol = symbol;
        this.formattedBalance = formatString();
    }

    private String formatString() {
        return this.getSymbol() + NumberFormat.getNumberInstance(Locale.US).format(this.getBalance());
    }
}