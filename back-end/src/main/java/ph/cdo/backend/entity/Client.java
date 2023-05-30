package ph.cdo.backend.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ph.cdo.backend.enums.Role;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "childBuilder")
public class Client extends User{

    private String mobilePhone;
    private Double balance;
    @Builder(builderMethodName = "childBuilder")
    public Client(Long id, String email, String password, boolean isEnabled, boolean isLocked) {
        super(id, email, password, Role.Client, isEnabled, isLocked);
    }

}
