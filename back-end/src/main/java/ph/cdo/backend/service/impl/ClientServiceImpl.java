package ph.cdo.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ph.cdo.backend.entity.user.Client;
import ph.cdo.backend.repository.ClientRepository;
import ph.cdo.backend.repository.UserRepository;
import ph.cdo.backend.service.ClientService;

import java.util.List;

@Service("ClientService")


public class ClientServiceImpl extends IUserServiceImpl<Client> implements ClientService {
    private final ClientRepository clientRepository;

    public ClientServiceImpl( @Autowired UserRepository<Client> userRepository, @Autowired ClientRepository clientRepository) {
        super(userRepository);
        this.clientRepository = clientRepository;
    }



}
