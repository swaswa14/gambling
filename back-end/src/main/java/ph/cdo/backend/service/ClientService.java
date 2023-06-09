package ph.cdo.backend.service;

import ph.cdo.backend.dto.records.ClientDTOEntity;
import ph.cdo.backend.entity.Transaction;
import ph.cdo.backend.entity.user.Client;
import ph.cdo.backend.request.ClientDepositRequest;
import ph.cdo.backend.request.ClientWithdrawalRequest;

public interface ClientService  extends IUserService<Client, ClientDTOEntity>{

    void addTransaction(Long id, Transaction transaction );

    void addTransaction(Client client, Transaction transaction );

    ClientDTOEntity deposit(Long id, ClientDepositRequest request);

    ClientDTOEntity withdraw(Long id, ClientWithdrawalRequest request);

}
