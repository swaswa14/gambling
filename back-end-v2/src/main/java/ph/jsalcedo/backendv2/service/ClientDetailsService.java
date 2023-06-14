package ph.jsalcedo.backendv2.service;

import ch.qos.logback.core.net.server.Client;
import ph.jsalcedo.backendv2.dto.user.TransactionDto;
import ph.jsalcedo.backendv2.dto.user.details.ClientDetailsDto;
import ph.jsalcedo.backendv2.entity.Transaction;
import ph.jsalcedo.backendv2.entity.user.details.ClientDetails;
import ph.jsalcedo.backendv2.request.ClientTransactionRequest;

import java.util.List;

public interface ClientDetailsService extends AbstractUserDetailsService<ClientDetails, ClientDetailsDto> {


    ClientDetailsDto deposit(Long id, ClientTransactionRequest request);

    ClientDetailsDto withdraw(Long id, ClientTransactionRequest request);

    void addTransaction(ClientDetails client, Transaction transaction);

    void addTransaction(Long id, Transaction transaction);

}
