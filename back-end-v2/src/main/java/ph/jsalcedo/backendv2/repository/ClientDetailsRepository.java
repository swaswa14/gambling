package ph.jsalcedo.backendv2.repository;

import org.springframework.stereotype.Repository;
import ph.jsalcedo.backendv2.dto.user.details.ClientDetailsDto;
import ph.jsalcedo.backendv2.entity.user.details.ClientDetails;

import java.util.List;

@Repository("ClientRepository")
public interface ClientDetailsRepository extends AbstractUserDetailsRepository<ClientDetails> {

    List<ClientDetails> findAllByInvitationCode(String invitationCode);

}
