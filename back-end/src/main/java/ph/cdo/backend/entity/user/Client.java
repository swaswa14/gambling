package ph.cdo.backend.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import ph.cdo.backend.entity.Transaction;

import ph.cdo.backend.enums.Role;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EnableJpaAuditing
@Data
@SequenceGenerator(name = "user_sequence", sequenceName = "client_id_seq", allocationSize = 1)

public class Client extends User{


    private Double balance;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    @JsonManagedReference
    private List<Transaction> transactions = new ArrayList<>();

    @JsonIgnore
    public List<Transaction> getTransactions() {
        return transactions;
    }

    @JsonIgnore
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void addToChildren(Transaction transaction){
        transaction.setClient(this);
        this.transactions.add(transaction);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
