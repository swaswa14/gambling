package ph.jsalcedo.backendv2.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ph.jsalcedo.backendv2.entity.Transaction;
import ph.jsalcedo.backendv2.entity.user.details.ClientDetails;

import java.util.List;

@Repository("TransactionRepository")
public interface TransactionRepository extends BaseEntityRepository<Transaction>{
    List<Transaction> findAllByClient(ClientDetails client, Pageable pageable);
}
