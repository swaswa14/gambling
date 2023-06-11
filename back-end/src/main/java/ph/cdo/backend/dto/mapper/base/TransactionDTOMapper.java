package ph.cdo.backend.dto.mapper.base;

import org.springframework.stereotype.Service;
import ph.cdo.backend.dto.records.TransactionDTO;
import ph.cdo.backend.entity.Transaction;

import java.math.RoundingMode;
import java.util.function.Function;


//todo test it
@Service
public class TransactionDTOMapper implements Function<Transaction, TransactionDTO> {
    @Override
    public TransactionDTO apply(Transaction transaction) {
        return new TransactionDTO(
                transaction.getId(),
                transaction.getCreateDate(),
                transaction.getTransactionType(),
                transaction.getValue().setScale(2, RoundingMode.HALF_EVEN),
                transaction.getClient().getEmail()
        );
    }
}
