package ph.cdo.backend.entity.base;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Name {
    private String firstName;
    private String lastName;

    @Override
    public String toString() {
        return (firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase()) + " " +
                (lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Name)) return false;

        Name name = (Name) o;

        return getLastName() != null ? getLastName().equals(name.getLastName()) : name.getLastName() == null;
    }

    @Override
    public int hashCode() {
        return getLastName() != null ? getLastName().hashCode() : 0;
    }
}
