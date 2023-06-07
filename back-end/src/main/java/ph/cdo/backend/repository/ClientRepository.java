package ph.cdo.backend.repository;

import org.springframework.stereotype.Repository;
import ph.cdo.backend.entity.user.Client;
import ph.cdo.backend.repository.noBean.UserRepository;

@Repository("ClientRepository")
public interface ClientRepository extends UserRepository<Client> {}
