package ph.jsalcedo.backendv2.service;

import ph.jsalcedo.backendv2.dto.user.details.AgentDetailsDto;
import ph.jsalcedo.backendv2.dto.user.details.ClientDetailsDto;
import ph.jsalcedo.backendv2.entity.user.details.AgentDetails;

import java.util.List;

public interface AgentDetailsService extends BaseEntityService<AgentDetails, AgentDetailsDto> {

    AgentDetailsDto generateAgentCode(AgentDetails agentDetails); //todo impl

    List<ClientDetailsDto> findClientsByAgentCode(String agentCode); //todo impl
}
