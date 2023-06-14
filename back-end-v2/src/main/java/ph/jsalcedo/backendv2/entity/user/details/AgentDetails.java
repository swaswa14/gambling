package ph.jsalcedo.backendv2.entity.user.details;

import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EnableJpaAuditing
@Getter
@Setter
@SequenceGenerator(name = "base_sequence", sequenceName = "agent_sequence", allocationSize = 1)
@Table(name = "agent")
public class AgentDetails extends AbstractUserDetails {
    private String agentCode;
}
