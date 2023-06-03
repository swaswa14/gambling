package ph.cdo.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ph.cdo.backend.entity.user.Agent;

@Repository("AgentRepository")
public interface AgentRepository extends UserRepository<Agent> {
}
