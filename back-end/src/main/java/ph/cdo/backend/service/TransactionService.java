package ph.cdo.backend.service;

import ph.cdo.backend.entity.Transaction;
import ph.cdo.backend.entity.TransactionType;
import ph.cdo.backend.entity.user.Client;

import java.util.Date;
import java.util.List;

public interface TransactionService {
    List<Transaction> findAllByClient(Client client);
    List<Transaction> findAll();

    List<Transaction> findByMonth(int month);
    List<Transaction> findByRangeAmount(int min, int max);

    List<Transaction> findByRangeDate(Date startDate, Date endDate);

    boolean findByID(Long id);

    List<Transaction> findByType(TransactionType transactionType);



}
