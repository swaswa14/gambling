package ph.cdo.backend.dto.records;

import ph.cdo.backend.enums.TransactionType;

import java.math.BigDecimal;
import java.util.Date;

public record TransactionDTO(
        Long id,
        Date createDate,
        TransactionType transactionType,
        BigDecimal value,
        String clientEmail
) {

}
