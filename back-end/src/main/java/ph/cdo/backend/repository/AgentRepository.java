package ph.cdo.backend.repository;

import org.springframework.stereotype.Repository;
import ph.cdo.backend.entity.user.Agent;
import ph.cdo.backend.repository.noBean.UserRepository;

@Repository("AgentRepository")
public interface AgentRepository extends UserRepository<Agent> {

    boolean existsByAgentCode(String invitationCode);
}
