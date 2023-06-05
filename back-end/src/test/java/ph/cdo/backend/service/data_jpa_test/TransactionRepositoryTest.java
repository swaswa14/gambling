package ph.cdo.backend.service.data_jpa_test;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import ph.cdo.backend.entity.Transaction;
import ph.cdo.backend.entity.user.Agent;
import ph.cdo.backend.entity.user.Client;
import ph.cdo.backend.enums.Role;
import ph.cdo.backend.enums.TransactionType;
import ph.cdo.backend.repository.AgentRepository;
import ph.cdo.backend.repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@DataJpaTest
@ActiveProfiles("test")
public class TransactionRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TransactionRepository transactionRepository;


    @Test
    public void testingIfEntityManagerNotNull(){
        Assertions.assertNotNull(entityManager);
    }

    @Test
    public void testIfRepositoryIsNotNull(){
        Assertions.assertNotNull(transactionRepository);
    }

    @Test
    public void testingIfCanSaveInRepo(){
        Client client = Client.builder()
                .email("test@example.com")
                .password("password")
                .mobilePhone("123456789")
                .role(Role.Client)
                .balance(BigDecimal.valueOf(100.0))
                .build();

        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.DEBIT)
                .value(BigDecimal.valueOf(100.00))
                .client(client)
                .build();

        transaction = transactionRepository.save(transaction);

        // Flush the changes to the database
        entityManager.flush();
        entityManager.clear();

        Assertions.assertNotNull(transaction);
        Assertions.assertEquals(transaction.getId(), 1L);
        Assertions.assertEquals(transaction.getTransactionType(), TransactionType.DEBIT);
        Assertions.assertEquals(transaction.getClient(), client);
        Assertions.assertEquals(transaction.getValue(), BigDecimal.valueOf(100.00));

        ;
    }



}
