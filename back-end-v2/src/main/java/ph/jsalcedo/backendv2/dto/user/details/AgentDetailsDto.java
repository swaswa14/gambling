package ph.jsalcedo.backendv2.dto.user.details;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AgentDetailsDto extends AbstractUserDetailsDto{

    private String agentCode;
}
