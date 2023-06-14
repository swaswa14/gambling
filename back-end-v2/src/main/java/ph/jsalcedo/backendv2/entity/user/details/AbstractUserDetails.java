package ph.jsalcedo.backendv2.entity.user.details;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ph.jsalcedo.backendv2.entity.BaseEntity;
import ph.jsalcedo.backendv2.entity.user.User;
import ph.jsalcedo.backendv2.model.BasicInformation;
import ph.jsalcedo.backendv2.model.Name;
import ph.jsalcedo.backendv2.model.Phone;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class AbstractUserDetails extends BaseEntity {

    @Embedded
    protected BasicInformation basicInformation;

    @OneToOne
    @JoinColumn(name="user_id")
    protected User user;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractUserDetails)) return false;
        if (!super.equals(o)) return false;
        AbstractUserDetails that = (AbstractUserDetails) o;
        return Objects.equals(basicInformation, that.basicInformation) &&
                Objects.equals(user.getId(), that.user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), basicInformation, user.getId());
    }
}
