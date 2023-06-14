package ph.jsalcedo.backendv2.service;

import ph.jsalcedo.backendv2.dto.user.TransactionDto;
import ph.jsalcedo.backendv2.entity.Transaction;
import ph.jsalcedo.backendv2.entity.user.details.ClientDetails;
import ph.jsalcedo.backendv2.model.TransactionType;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

public interface TransactionService extends BaseEntityService<Transaction, TransactionDto>{

    List<TransactionDto> findAllByClient(ClientDetails client, int page, int size);
    List<TransactionDto> findAllByClient(Long id, int page, int size);
    List<TransactionDto> findAll(int page, int size);

    List<TransactionDto> findByMonth(int month, int year, int page, int size);
    List<TransactionDto> findByRangeAmount(double min, double max, int page, int size);

    List<TransactionDto> findByRangeDate(Date startDate, Date endDate, int page, int size);

    TransactionDto findByID(Long id);

    List<TransactionDto> findByType(TransactionType transactionType, int page, int size);



    boolean deleteTransactionById(Long id);
}
