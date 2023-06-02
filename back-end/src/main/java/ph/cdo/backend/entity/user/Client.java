package ph.cdo.backend.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import ph.cdo.backend.entity.Transaction;
import ph.cdo.backend.entity.wallet.ClientWallet;
import ph.cdo.backend.enums.Role;

import java.util.ArrayList;
import java.util.List;

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




}
