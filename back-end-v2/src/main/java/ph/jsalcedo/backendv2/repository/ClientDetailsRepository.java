package ph.jsalcedo.backendv2.repository;

import org.springframework.stereotype.Repository;
import ph.jsalcedo.backendv2.entity.user.details.ClientDetails;

@Repository("ClientRepository")
public interface ClientDetailsRepository extends AbstractUserDetailsRepository<ClientDetails> {

}
