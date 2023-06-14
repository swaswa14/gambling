package ph.jsalcedo.backendv2.mapper.userdetails;

import org.springframework.stereotype.Service;
import ph.jsalcedo.backendv2.dto.user.details.AgentDetailsDto;
import ph.jsalcedo.backendv2.entity.user.details.AgentDetails;

@Service("AgentEntityDtoMapper")
//todo test
public class AgentEntityDtoMapper<T extends AgentDetails, R extends AgentDetailsDto> extends AbstractUserDetailsEntityToDtoMapper<T, R> {
    @Override
    @SuppressWarnings("unchecked")
    protected R createDto() {
        return (R) new AgentDetailsDto();
    }

    @Override
    public R apply(T t) {
        R dto =  super.apply(t);
        dto.setAgentCode(t.getAgentCode());
        return dto;
    }
}
