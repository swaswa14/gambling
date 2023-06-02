package ph.cdo.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ph.cdo.backend.entity.user.Agent;
import ph.cdo.backend.repository.AgentRepository;
import ph.cdo.backend.repository.UserRepository;
import ph.cdo.backend.service.AgentService;

@Service("AgentService")
public class AgentServiceImpl extends IUserServiceImpl<Agent> implements AgentService {
    private final AgentRepository agentRepository;

    public AgentServiceImpl( @Autowired UserRepository<Agent> userRepository,  @Autowired AgentRepository agentRepository) {
        super(userRepository);
        this.agentRepository = agentRepository;
    }
}
