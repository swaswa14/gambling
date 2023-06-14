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
public class Phone {
    private String countryCode;
    private String number;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone = (Phone) o;
        return Objects.equals(countryCode, phone.countryCode) &&
                Objects.equals(number, phone.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryCode, number);
    }
}
