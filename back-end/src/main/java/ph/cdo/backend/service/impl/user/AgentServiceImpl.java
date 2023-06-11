package ph.cdo.backend.service.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ph.cdo.backend.dto.records.AgentDTOEntity;
import ph.cdo.backend.dto.mapper.impl.user.AgentDTOMapper;
import ph.cdo.backend.entity.user.Agent;
import ph.cdo.backend.repository.AgentRepository;
import ph.cdo.backend.service.AgentService;
import ph.cdo.backend.service.EmailService;
import ph.cdo.backend.service.impl.base_entity.IUserServiceImpl;

@Service("AgentService")
public class AgentServiceImpl extends IUserServiceImpl<Agent, AgentDTOEntity, AgentDTOMapper> implements AgentService {

    private final EmailService emailService;

    public AgentServiceImpl(
            @Autowired @Qualifier("AgentRepository") AgentRepository agentRepository,
            @Autowired @Qualifier("AgentDTOMapper") AgentDTOMapper userDTOMapper,

            EmailService emailService) {
        super(userDTOMapper, agentRepository, emailService);

        this.emailService = emailService;
    }
}
