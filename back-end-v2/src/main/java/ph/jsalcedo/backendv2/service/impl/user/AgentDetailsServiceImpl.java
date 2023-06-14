package ph.jsalcedo.backendv2.service.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ph.jsalcedo.backendv2.dto.user.details.AgentDetailsDto;
import ph.jsalcedo.backendv2.dto.user.details.ClientDetailsDto;
import ph.jsalcedo.backendv2.entity.user.details.AgentDetails;
import ph.jsalcedo.backendv2.mapper.userdetails.AgentEntityDtoMapper;
import ph.jsalcedo.backendv2.repository.AbstractUserDetailsRepository;
import ph.jsalcedo.backendv2.repository.AgentDetailsRepository;
import ph.jsalcedo.backendv2.service.AgentDetailsService;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AgentDetailsServiceImpl extends AbstractUserDetailsServiceImpl<AgentDetails, AgentDetailsDto,
        AgentEntityDtoMapper<AgentDetails, AgentDetailsDto>> implements AgentDetailsService {

    protected  final AgentDetailsRepository repository;
    @Autowired
    public AgentDetailsServiceImpl( AgentDetailsRepository repository,
                                   @Qualifier("AgentEntityDtoMapper")   AgentEntityDtoMapper<AgentDetails, AgentDetailsDto> mapper) {
        super(repository, mapper);
        this.repository = repository;
    }

    @Override
    public AgentDetailsDto generateAgentCode(AgentDetails agentDetails) {
        Optional<AgentDetails> optionalAgentDetails = null;
        String agentCode = null;
        do{
            agentCode = generateCode();
            optionalAgentDetails = repository.findByAgentCode(agentCode);

        }while (optionalAgentDetails.isPresent());

        agentDetails.setAgentCode(agentCode);
        return mapper.apply(repository.save(agentDetails));

    }

    @Override
    public List<ClientDetailsDto> findClientsByAgentCode(String agentCode) {
        return null;
    }

    private String generateCode(){
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }
}
