package ph.cdo.backend.entity.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ph.cdo.backend.enums.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class Agent extends User{

    private String agentCode;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Agent agent)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(agentCode, agent.agentCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), agentCode);
    }

}
