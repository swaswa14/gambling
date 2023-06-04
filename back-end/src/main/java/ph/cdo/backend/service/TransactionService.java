package ph.cdo.backend.service;

import ph.cdo.backend.entity.Transaction;
import ph.cdo.backend.enums.TransactionType;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

public interface TransactionService {
    List<Transaction> findAllByClientID(Long id);
    List<Transaction> findAll();

    List<Transaction> findByMonth(int month, int year);
    List<Transaction> findByRangeAmount(double min, double maxr);

    List<Transaction> findByRangeDate(Date startDate, Date endDate);

    Transaction findByID(Long id);

    List<Transaction> findByType(TransactionType transactionType);


    List<Transaction> sortedList(List<Transaction> list, Comparator<Transaction> comparator);

    boolean deleteTransactionById(Long id);



}
