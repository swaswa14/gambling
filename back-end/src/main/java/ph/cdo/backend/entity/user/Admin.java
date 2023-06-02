package ph.cdo.backend.entity.user;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ph.cdo.backend.enums.Role;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Admin extends Agent{

    private String name;

}
