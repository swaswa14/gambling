package ph.cdo.backend.entity.user;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ph.cdo.backend.enums.Role;

import java.util.Objects;

@Entity
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class Admin extends Agent{







}
