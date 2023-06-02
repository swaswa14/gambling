package ph.cdo.backend.entity.user;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import ph.cdo.backend.enums.Role;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "childBuilder")
public class Admin extends Agent{

    @Builder(builderMethodName = "childBuilder")

    public Admin(Long id, String email, String password, boolean isEnabled, boolean isLocked) {
        super(id, email, password, isEnabled, isLocked);
        this.setRole(Role.Admin);
    }


}
