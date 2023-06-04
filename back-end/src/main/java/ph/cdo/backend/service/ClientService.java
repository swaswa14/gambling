package ph.cdo.backend.service;

import ph.cdo.backend.dto.ClientDTO;
import ph.cdo.backend.dto.mapper.ClientDTOMapper;
import ph.cdo.backend.entity.Transaction;
import ph.cdo.backend.entity.user.Client;

import java.util.List;

public interface ClientService  extends IUserService<Client, ClientDTO>{

    void addTransaction(Long id, Transaction transaction );

    void addTransaction(Client client, Transaction transaction );

}
