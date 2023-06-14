package ph.jsalcedo.backendv2.mapper.userdetails;

import org.springframework.stereotype.Service;
import ph.jsalcedo.backendv2.dto.user.TransactionDto;
import ph.jsalcedo.backendv2.entity.Transaction;
import ph.jsalcedo.backendv2.mapper.EntityToDtoMapper;

@Service
public class TransactionDtoMapper extends EntityToDtoMapper<Transaction, TransactionDto> {
    @Override
    public TransactionDto apply(Transaction transaction) {
        return new TransactionDto(
                transaction.getId(),
                transaction.getCreateDate(),
                transaction.getType(),
                transaction.getAmount()
        );
    }
}
