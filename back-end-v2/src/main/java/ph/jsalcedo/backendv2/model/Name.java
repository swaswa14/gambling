package ph.jsalcedo.backendv2.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Name {

    private String firstname;

    private String lastname;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name = (Name) o;
        return Objects.equals(lastname, name.lastname) &&
                Objects.equals(firstname, name.firstname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastname, firstname);
    }
}
