package ph.cdo.backend.dto.mapper;

import org.springframework.stereotype.Service;
import ph.cdo.backend.dto.TransactionDTO;
import ph.cdo.backend.entity.Transaction;

import java.math.RoundingMode;
import java.util.function.Function;

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
