package ph.cdo.backend.service;

import ph.cdo.backend.dto.TransactionDTO;
import ph.cdo.backend.entity.Transaction;
import ph.cdo.backend.enums.TransactionType;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

public interface TransactionService {
    List<TransactionDTO> findAllByClientID(Long id);
    List<TransactionDTO> findAll();

    List<TransactionDTO> findByMonth(int month, int year);
    List<TransactionDTO> findByRangeAmount(double min, double maxr);

    List<TransactionDTO> findByRangeDate(Date startDate, Date endDate);

    TransactionDTO findByID(Long id);

    List<TransactionDTO> findByType(TransactionType transactionType);


    List<TransactionDTO> sortedList(List<TransactionDTO> list, Comparator<TransactionDTO> comparator);

    boolean deleteTransactionById(Long id);



}
