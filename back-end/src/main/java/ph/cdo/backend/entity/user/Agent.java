package ph.cdo.backend.entity.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import ph.cdo.backend.enums.Role;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "childBuilder")
public class Agent extends User{

    private String agentCode;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Builder.Default
    @JsonManagedReference
    private List<Client> clientList = new ArrayList<>();

    @Builder(builderMethodName = "childBuilder")
    public Agent(Long id, String email, String password, boolean isEnabled, boolean isLocked) {
        super(id, email, password, Role.Agent, isEnabled, isLocked);
    }


}
