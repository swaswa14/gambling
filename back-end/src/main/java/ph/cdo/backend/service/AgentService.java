package ph.cdo.backend.service;

import ph.cdo.backend.dto.records.AgentDTOEntity;
import ph.cdo.backend.entity.user.Agent;
import ph.cdo.backend.service.impl.user.IUserService;

public interface AgentService extends IUserService<Agent, AgentDTOEntity> {
}
