package ph.jsalcedo.backendv2.service.impl.user;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ph.jsalcedo.backendv2.dto.user.TransactionDto;
import ph.jsalcedo.backendv2.dto.user.details.ClientDetailsDto;
import ph.jsalcedo.backendv2.entity.Transaction;
import ph.jsalcedo.backendv2.entity.user.details.ClientDetails;
import ph.jsalcedo.backendv2.exceptions.EntityDoesNotExistsException;
import ph.jsalcedo.backendv2.exceptions.InsufficientFundsException;
import ph.jsalcedo.backendv2.exceptions.InvalidDepositAmountException;
import ph.jsalcedo.backendv2.exceptions.InvalidWithdrawalAmountException;
import ph.jsalcedo.backendv2.mapper.userdetails.ClientEntityDtoMapper;
import ph.jsalcedo.backendv2.mapper.userdetails.TransactionDtoMapper;
import ph.jsalcedo.backendv2.model.TransactionType;
import ph.jsalcedo.backendv2.repository.ClientDetailsRepository;
import ph.jsalcedo.backendv2.request.ClientTransactionRequest;
import ph.jsalcedo.backendv2.service.ClientDetailsService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service("ClientDetailsService")
@Slf4j

public  class ClientDetailsDetailsServiceImpl extends AbstractUserDetailsServiceImpl<ClientDetails, ClientDetailsDto,
        ClientEntityDtoMapper> implements ClientDetailsService {

    private final ClientDetailsRepository repository;


    @Autowired
    public ClientDetailsDetailsServiceImpl(@Qualifier("ClientRepository") ClientDetailsRepository repository,
                                           @Qualifier("ClientEntityDtoMapper") ClientEntityDtoMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
    }


    @Transactional
    @Override
    public void addTransaction(Long id, Transaction transaction) {
        var client = super.repository.findById(id)
                .orElseThrow(()-> new EntityDoesNotExistsException(id));

        addTransaction(client, transaction);
    }


    @Override
    @Transactional
    public void addTransaction(ClientDetails client, Transaction transaction) {

        transaction.setClient(client);
        client.getTransactions().add(transaction);


        double currentBalance =  client.getBalance().doubleValue();
        double transactionAmount = transaction.getAmount().doubleValue();
        client.setBalance(
                BigDecimal.valueOf(
                        transaction.getType() == TransactionType.DEBIT ? currentBalance + transactionAmount:
                                currentBalance - transactionAmount));

        repository.saveAndFlush(client);
    }

    @Override
    @Transactional
    public ClientDetailsDto deposit(Long id, ClientTransactionRequest request) {
        if(request.getAmount().doubleValue() <= 0)
            throw new InvalidDepositAmountException();
        Transaction transaction = Transaction
                .builder()
                .amount(request.getAmount())
                .type(TransactionType.DEBIT)
                .build();
        addTransaction(id, transaction);

        return this.findById(id);
    }

    @Override
    @Transactional
    public ClientDetailsDto withdraw(Long id, ClientTransactionRequest request) {
        ClientDetails client = repository.findById(id).orElseThrow(()-> new EntityDoesNotExistsException(id));
        if(request.getAmount().doubleValue() <= 0L)
            throw new InvalidWithdrawalAmountException();
        if(client.getBalance().doubleValue() < request.getAmount().doubleValue())
            throw new InsufficientFundsException("Insufficient funds!");


        Transaction transaction = Transaction
                .builder()
                .amount(request.getAmount())
                .type(TransactionType.CREDIT)
                .build();
        addTransaction(client, transaction);

        return this.findById(id);
    }

//    @Override
//    public List<TransactionDto> getAllTransactions(Long id) {
//        ClientDetails client = repository.findById(id).orElseThrow(()-> new EntityDoesNotExistsException(id));
//        return client.getTransactions().stream()
//                .map(transactionDtoMapper)
//                .collect(Collectors.toList());
//    }


}
