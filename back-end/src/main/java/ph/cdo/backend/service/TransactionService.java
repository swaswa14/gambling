package ph.cdo.backend.service;

import ph.cdo.backend.entity.Transaction;
import ph.cdo.backend.entity.TransactionType;
import ph.cdo.backend.entity.user.Client;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

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
