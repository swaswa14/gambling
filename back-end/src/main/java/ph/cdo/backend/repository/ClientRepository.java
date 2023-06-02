package ph.cdo.backend.repository;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ph.cdo.backend.entity.user.Client;

@Repository
public interface ClientRepository extends UserRepository<Client> {



}
