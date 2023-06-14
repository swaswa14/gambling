package ph.jsalcedo.backendv2.model;

import jakarta.persistence.*;
import lombok.*;
import ph.jsalcedo.backendv2.entity.user.User;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@Builder
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class BasicInformation {
    private String email;
    @Embedded
    private Name name;

    @Embedded
    private Phone phone;

    @Temporal(TemporalType.DATE)
    private Date birthday;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicInformation that = (BasicInformation) o;
        return Objects.equals(email, that.email) &&
                Objects.equals(name, that.name) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(birthday, that.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, name, phone, birthday);
    }

}
