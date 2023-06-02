package ph.cdo.backend.entity.wallet;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientWallet {


    @Embedded
    private Crypto cryptoWallet;




//    public BigDecimal calculateTotalBalance() {
//        return cashWallet.getBalance()
//                .add(cryptoWallet.getBalance())
//                .add(pendingTransfer);
//    }

    // Other getters, setters and methods
}