package ph.cdo.backend.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import ph.cdo.backend.enums.Role;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "childBuilder")
public class Agent extends User{

    @Builder(builderMethodName = "childBuilder")
    public Agent(Long id, String email, String password, boolean isEnabled, boolean isLocked) {
        super(id, email, password, Role.Agent, isEnabled, isLocked);
    }
}
