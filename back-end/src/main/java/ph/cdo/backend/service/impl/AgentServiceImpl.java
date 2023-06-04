package ph.cdo.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ph.cdo.backend.dto.AgentDTO;
import ph.cdo.backend.dto.mapper.AgentDTOMapper;
import ph.cdo.backend.entity.user.Agent;
import ph.cdo.backend.repository.AgentRepository;
import ph.cdo.backend.service.AgentService;

@Service("AgentService")
public class AgentServiceImpl extends IUserServiceImpl<Agent, AgentDTO, AgentDTOMapper> implements AgentService {



    public AgentServiceImpl(
            @Autowired @Qualifier("AgentRepository") AgentRepository agentRepository,
            @Autowired @Qualifier("AgentDTOMapper") AgentDTOMapper userDTOMapper

    ) {
        super(userDTOMapper, agentRepository);

    }
}
