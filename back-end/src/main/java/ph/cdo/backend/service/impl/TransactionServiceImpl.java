package ph.cdo.backend.service.impl;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ph.cdo.backend.entity.Transaction;
import ph.cdo.backend.entity.TransactionType;
import ph.cdo.backend.entity.user.Client;
import ph.cdo.backend.errors.EmptyListException;
import ph.cdo.backend.errors.EntityDoesNotExistsException;
import ph.cdo.backend.errors.InvalidValueException;
import ph.cdo.backend.errors.NullEntityException;
import ph.cdo.backend.repository.ClientRepository;
import ph.cdo.backend.repository.TransactionRepository;
import ph.cdo.backend.service.TransactionService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service

public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    @Qualifier("ClientRepository")
    private final ClientRepository clientRepository;


    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, ClientRepository clientRepository) {
        this.transactionRepository = transactionRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Transaction> findAllByClientID(Long id) throws EntityDoesNotExistsException, EmptyListException {
        Client client = clientRepository.findById(id)
                .orElseThrow(()-> new EntityDoesNotExistsException(id));

        var list =  findAll()
                .stream()
                .filter(t -> t.getClient().equals(client))
                .collect(Collectors.toList());
        if(list.size() == 0)
            throw new EmptyListException("List of Transactions by Client");
        return list;

    }

    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> findByMonth(int month, int year) throws EmptyListException, InvalidValueException{
        if(month >= 13 || month <= 0 || year <= 0)
            throw new InvalidValueException(month, year);

        //Converts the Date To Local Date then Get it's value.
        var list =  findAll()
                .stream()
                .filter(t-> {
                 LocalDate localDate = t.getCreateDate()
                         .toInstant()
                         .atZone(ZoneId.systemDefault())
                         .toLocalDate();
                 int currentMonth = localDate.getMonthValue();
                 int currentYear = localDate.getYear();
                 return (currentMonth ==  month && currentYear == year);
                })
                .toList();
        if(list.size() == 0)
            throw new EmptyListException("List of Transactions by Month");
        return list;
    }

    @Override
    public List<Transaction> findByRangeAmount(double min, double max) throws InvalidValueException, EmptyListException{
        if(min > max || min <= 0 || max <= 0 )
            throw new InvalidValueException(min, max);
        var list = findAll().stream()
                .filter(t-> (t.getValue() >= min && t.getValue() <= max))
                .toList();
        if(list.size() == 0)
            throw new EmptyListException("List of Transactions by Range Amount");
        return list;


    }

    @Override
    public List<Transaction> findByRangeDate(Date startDate, Date endDate) throws InvalidValueException, EmptyListException{
        if( startDate == null || endDate == null || startDate.after(endDate))
            throw new InvalidValueException(startDate, endDate);
        var list = findAll().stream()
                .filter(t-> {
                    return
                            (t.getCreateDate().after(startDate) || t.getCreateDate().compareTo(startDate) == 0)
                            &&
                                    (t.getCreateDate().before(endDate) || t.getCreateDate().compareTo(endDate) == 0);
                })
                .toList();

        if(list.size() == 0)
            throw new EmptyListException("List of Transactions by a range of Date");

        return list;
    }

    @Override
    public Transaction findByID(Long id) throws EntityDoesNotExistsException{
        return transactionRepository.findById(id).
                orElseThrow(()-> new EntityDoesNotExistsException(id));
    }

    @Override
    public List<Transaction> findByType(TransactionType transactionType) throws EmptyListException{
        var list =  findAll().stream()
                .filter(t-> t.getTransactionType().equals(transactionType))
                .toList();
        if(list.size() == 0)
            throw new EmptyListException("List of Transactions by Transaction type");
        return list;
    }

    @Override
    public List<Transaction> sortedList(List<Transaction> list, Comparator<Transaction> comparator) {
        var temp = new java.util.ArrayList<>(List.copyOf(list));
         temp.sort(comparator);

         return temp;
    }

    @Override
    public boolean deleteTransactionById(Long id) throws InvalidValueException, EntityDoesNotExistsException {
        // checking if the value is valid
        if (id == null || id <= 0)
            throw new InvalidValueException(id);

        // Check if the transaction exists
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new EntityDoesNotExistsException(id));

        // Delete the transaction
        transactionRepository.delete(transaction);

        // Verify if the transaction is removed from the DB
        return transactionRepository.findById(transaction.getId()).isEmpty();

    }













}
