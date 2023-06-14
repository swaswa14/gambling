package ph.jsalcedo.backendv2.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import ph.jsalcedo.backendv2.entity.user.details.ClientDetails;
import ph.jsalcedo.backendv2.model.TransactionType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@SequenceGenerator(name = "base_sequence", sequenceName = "transaction_sequence", allocationSize = 1)
@Table(name="transaction")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction extends BaseEntity{


    @Temporal(TemporalType.DATE)
    @Column(name="create_date")
    private Date createDate;


    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name="client_id")
    @JsonBackReference
    private ClientDetails client;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        if (!super.equals(o)) return false;
        Transaction that = (Transaction) o;
        return type == that.type &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(client.getId(), that.client.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), type, amount, client.getId());
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + this.getId() +
                "createDate=" + createDate +
                ", type=" + type +
                ", amount=" + amount +
                ", client=" + (client.getId() == null ? "N/A" : client.getId()) +
                '}';
    }
}
