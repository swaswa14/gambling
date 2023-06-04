package ph.cdo.backend.dto.mapper;

import ph.cdo.backend.dto.AgentDTO;
import ph.cdo.backend.dto.ClientDTO;
import ph.cdo.backend.entity.user.Agent;

import java.math.RoundingMode;
import java.util.function.Function;

public class AgentDTOMapper extends UserDTOMapper implements Function<Agent, AgentDTO> {
    @Override
    public AgentDTO apply(Agent agent) {
        return new AgentDTO(
                agent.getId(),
                agent.getRole(),
                agent.getEmail(),
                agent.getMobilePhone(),
                agent.getAgentCode()
        );
    }
}
