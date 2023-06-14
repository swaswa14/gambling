package ph.jsalcedo.backendv2.repository;

import org.springframework.stereotype.Repository;
import ph.jsalcedo.backendv2.entity.user.details.AgentDetails;

import java.util.Optional;

@Repository("AgentRepository")
public interface AgentDetailsRepository extends AbstractUserDetailsRepository<AgentDetails>{
    Optional<AgentDetails> findByAgentCode(String agentCode);
}
