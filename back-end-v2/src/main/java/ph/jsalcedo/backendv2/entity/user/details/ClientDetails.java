package ph.jsalcedo.backendv2.entity.user.details;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import ph.jsalcedo.backendv2.entity.Transaction;
import ph.jsalcedo.backendv2.model.TransactionType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EnableJpaAuditing
@Getter
@Setter
@SequenceGenerator(name = "base_sequence", sequenceName = "client_sequence", allocationSize = 1)
@Table(name = "client")
public final class ClientDetails extends AbstractUserDetails{
    private BigDecimal balance;


    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @Builder.Default
    @JsonManagedReference
    private List<Transaction> transactions = new ArrayList<>();

    @JsonIgnore
    public List<Transaction> getTransactions() {
        return transactions;
    }



}
