package ph.cdo.backend.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ph.cdo.backend.dto.records.ClientDTOEntity;
import ph.cdo.backend.dto.mapper.impl.ClientDTOMapper;
import ph.cdo.backend.entity.Transaction;
import ph.cdo.backend.entity.user.Client;
import ph.cdo.backend.enums.TransactionType;
import ph.cdo.backend.exceptions.EntityDoesNotExistsException;
import ph.cdo.backend.exceptions.InsufficientFundsException;
import ph.cdo.backend.repository.ClientRepository;
import ph.cdo.backend.request.ClientDepositRequest;
import ph.cdo.backend.request.ClientWithdrawalRequest;
import ph.cdo.backend.service.ClientService;
import ph.cdo.backend.service.EmailService;
import ph.cdo.backend.service.impl.base_entity.IUserServiceImpl;

@Service("ClientService")


public class ClientServiceImpl extends IUserServiceImpl<Client, ClientDTOEntity, ClientDTOMapper> implements ClientService {

    private final EmailService emailService;

    public ClientServiceImpl(
            @Autowired @Qualifier("ClientRepository") ClientRepository clientRepository,
            @Autowired @Qualifier("ClientDTOMapper") ClientDTOMapper userDTOMapper,
            EmailService emailService) {
        super(userDTOMapper, clientRepository, emailService);

        this.emailService = emailService;
    }


    @Override
    @Transactional
    public void addTransaction(Long id, Transaction transaction) {
        var client = super.userRepository.findById(id)
                .orElseThrow(()-> new EntityDoesNotExistsException(id));

        client.addToChildren(transaction);
        super.userRepository.save(client);
    }

    @Override
    public void addTransaction(Client client, Transaction transaction) {
        client.addToChildren(transaction);
        super.userRepository.save(client);
    }

    @Override
    public ClientDTOEntity deposit(Long id, ClientDepositRequest request) {
        Client client = userRepository.findById(id).orElseThrow(()-> new EntityDoesNotExistsException(id));
        Transaction transaction = Transaction
                .builder()
                .value(request.getAmount())
                .transactionType(TransactionType.DEBIT)
                .build();
        addTransaction(client, transaction);

        return this.retrieve(client.getId());


    }

    @Override
    public ClientDTOEntity withdraw(Long id, ClientWithdrawalRequest request) {
        Client client = userRepository.findById(id).orElseThrow(()-> new EntityDoesNotExistsException(id));

        if(client.getBalance().doubleValue() < request.getAmount().doubleValue())
            throw new InsufficientFundsException("Insufficient funds!");

        Transaction transaction = Transaction
                .builder()
                .value(request.getAmount())
                .transactionType(TransactionType.CREDIT)
                .build();
        addTransaction(client, transaction);

        return this.retrieve(client.getId());
    }


}
