package ph.cdo.backend.dto.mapper.impl.registration;

import org.springframework.stereotype.Service;
import ph.cdo.backend.entity.base.Name;
import ph.cdo.backend.entity.user.Agent;
import ph.cdo.backend.enums.Role;
import ph.cdo.backend.request.registration.AgentBasicRegistrationForm;

@Service("AgentRegistrationMapper")
public class AgentRegistrationMapper extends UserRegistrationMapper<Agent, AgentBasicRegistrationForm> {
    @Override
    public Agent apply(AgentBasicRegistrationForm registrationForm) {
        Agent agent = super.apply(registrationForm);
        agent.setRole(Role.Agent);
        return agent;
    }
}
