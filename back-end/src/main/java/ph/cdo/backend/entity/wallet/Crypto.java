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
public final class Crypto extends Wallet {
    private String logo;
    private BigDecimal cryptoEquivalent;


    @Builder(builderMethodName = "childBuilder")
    public Crypto(BigDecimal balance, String logo, BigDecimal cryptoEquivalent) {
        super(balance );
        this.logo = logo;
        this.cryptoEquivalent = cryptoEquivalent;
        this.formattedBalance = formatString();
    }

    private String formatString() {
        return this.logo + cryptoEquivalent;
    }
}
