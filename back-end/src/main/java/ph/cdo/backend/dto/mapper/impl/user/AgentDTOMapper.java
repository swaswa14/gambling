package ph.cdo.backend.dto.mapper.impl.user;

import org.springframework.stereotype.Service;
import ph.cdo.backend.dto.records.AgentDTOEntity;
import ph.cdo.backend.entity.user.Agent;

@Service("AgentDTOMapper")
public class AgentDTOMapper extends UserDTOMapper<Agent, AgentDTOEntity> {
    @Override
    public AgentDTOEntity apply(Agent agent) {
        return new AgentDTOEntity(
                agent.getId(),
                agent.getRole(),
                agent.getEmail(),
                agent.getMobilePhone(),
                agent.getAgentCode(),
                agent.getName()
        );
    }
}
