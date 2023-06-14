package ph.jsalcedo.backendv2.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ph.jsalcedo.backendv2.dto.user.TransactionDto;
import ph.jsalcedo.backendv2.entity.Transaction;
import ph.jsalcedo.backendv2.entity.user.details.ClientDetails;
import ph.jsalcedo.backendv2.exceptions.EntityDoesNotExistsException;
import ph.jsalcedo.backendv2.exceptions.InvalidValueException;
import ph.jsalcedo.backendv2.mapper.userdetails.TransactionDtoMapper;
import ph.jsalcedo.backendv2.model.TransactionType;
import ph.jsalcedo.backendv2.repository.ClientDetailsRepository;
import ph.jsalcedo.backendv2.repository.TransactionRepository;
import ph.jsalcedo.backendv2.service.TransactionService;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl extends BaseEntityServiceImpl<Transaction, TransactionDto,
        TransactionDtoMapper> implements TransactionService {

    private TransactionRepository repository;
    private final ClientDetailsRepository clientDetailsRepository;
    @Autowired
    public TransactionServiceImpl(TransactionRepository repository, TransactionDtoMapper mapper, ClientDetailsRepository clientDetailsRepository) {
        super(repository, mapper);
        this.repository = repository;

        this.clientDetailsRepository = clientDetailsRepository;
    }

    @Override
    public List<TransactionDto> findAllByClient(ClientDetails client, int page, int size) {

       return  repository.findAllByClient(client, PageRequest.of(page, size, Sort.by("id").descending()))
                .stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDto> findAllByClient(Long id, int page, int size) {
        ClientDetails details =
                clientDetailsRepository.findById(id).orElseThrow(()-> new EntityDoesNotExistsException(id));
        return findAllByClient(details, page, size);
    }

    @Override
    public List<TransactionDto> findAll(int page, int size) {
        return repository.findAll(PageRequest.of(page, size, Sort.by("id").descending()))
                .stream()
                .map(mapper)
                .collect(Collectors.toList());

    }

    @Override
    public List<TransactionDto> findByMonth(int month, int year, int page, int size) {

        if(month >= 13 || month <= 0 || year <= 0)
            throw new InvalidValueException(month, year);

        //Converts the Date To Local Date then Get it's value.
        return findAll(page, size)
                .stream()
                .filter(t -> {
                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(ZoneId.systemDefault()));
                    cal.setTime(t.createDate());
                    int currentMonth = cal.get(Calendar.MONTH) + 1; // corrected line
                    int currentYear = cal.get(Calendar.YEAR); // corrected line
                    return (currentMonth ==  month && currentYear == year);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDto> findByRangeAmount(double min, double max, int page, int size) {
        if(min > max || min < 0 || max < 0 )
            throw new InvalidValueException(min, max);
        return findAll(page,size).stream()
                .filter(t-> (t.amount().doubleValue() >= min && t.amount().doubleValue() <= max))
                .toList();


    }

    @Override
    public List<TransactionDto> findByRangeDate(Date startDate, Date endDate, int page, int size) {
        if( startDate == null || endDate == null || startDate.after(endDate))
            throw new InvalidValueException(startDate, endDate);
        return findAll(page, size).stream()
                .filter(t-> {
                    return
                            (t.createDate().after(startDate) || t.createDate().compareTo(startDate) == 0)
                                    &&
                                    (t.createDate().before(endDate) || t.createDate().compareTo(endDate) == 0);
                })
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDto findByID(Long id) {
        return mapper.apply(repository.findById(id).orElseThrow(()-> new EntityDoesNotExistsException(id)));
    }

    @Override
    public List<TransactionDto> findByType(TransactionType transactionType, int page, int size) {
      return findAll(page, size).stream()
                .filter(t-> t.type().equals(transactionType))
                .toList();
    }

    @Override
    @Transactional
    public boolean deleteTransactionById(Long id) {
        // checking if the value is valid
        if (id == null || id <= 0)
            throw new InvalidValueException(id);

        // Check if the transaction exists
        Transaction transaction =  repository.findById(id)
                .orElseThrow(() -> new EntityDoesNotExistsException(id));

        // Delete the transaction
        repository.delete(transaction);

        // Verify if the transaction is removed from the DB
        return repository.findById(transaction.getId()).isEmpty();
    }
}
