package ph.cdo.backend.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ph.cdo.backend.entity.Transaction;
import ph.cdo.backend.entity.user.Client;
import ph.cdo.backend.errors.EntityDoesNotExistsException;
import ph.cdo.backend.repository.ClientRepository;
import ph.cdo.backend.repository.UserRepository;
import ph.cdo.backend.service.ClientService;

@Service("ClientService")


public class ClientServiceImpl extends IUserServiceImpl<Client> implements ClientService {


    public ClientServiceImpl( @Autowired @Qualifier("ClientRepository") ClientRepository clientRepository) {
        super(clientRepository);

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


}
