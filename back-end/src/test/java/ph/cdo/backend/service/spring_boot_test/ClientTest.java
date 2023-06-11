package ph.cdo.backend.service.spring_boot_test;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ph.cdo.backend.entity.Transaction;
import ph.cdo.backend.enums.TransactionType;
import ph.cdo.backend.entity.user.Client;
import ph.cdo.backend.enums.Role;
import ph.cdo.backend.repository.ClientRepository;
import ph.cdo.backend.service.impl.user.ClientService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")

public class ClientTest {

    @Qualifier("ClientService")
    private final ClientService clientService;

    @Qualifier("ClientRepository")
    private final ClientRepository clientRepository;
    private final Faker faker;

    private List<Client> clientList;

    @Autowired
    public ClientTest(ClientService clientService, ClientRepository clientRepository, Faker faker) {
        this.clientService = clientService;
        this.clientRepository = clientRepository;
        this.faker = faker;
    }


    @BeforeEach
    public void init(){

        this.clientList = new ArrayList<>();

        for(int i = 0; i < faker.number().numberBetween(10,20); i++){
            var temp = createRandomClient();
            clientList.add(temp);
            var tempDTO = clientService.save(temp);
            for(int y = 0; y< faker.number().numberBetween(1,50); y++ ){
                clientService.addTransaction(temp.getId(), createRandomTransaction());
            }
        }
    }

    @AfterEach
    public void reset(){
        this.clientRepository.deleteAll();
    }

    @Test
    public void testingIfArrayIsEmpty(){
        Assertions.assertTrue(clientRepository.findAll().size() > 0);
    }








    public  Client createRandomClient() {
        return Client.builder()
                .role(Role.Client)
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .mobilePhone(faker.phoneNumber().cellPhone())
                .balance(BigDecimal.valueOf(faker.number().randomDouble(2, 0, 10000)))
                .isEnabled(true)
                .isLocked(false)
                .build();
    }


    public  Transaction createRandomTransaction() {
        Faker faker = new Faker();

        return Transaction.builder()
                .transactionType(TransactionType.values()[faker.number().numberBetween(0, TransactionType.values().length)])
                .value(BigDecimal.valueOf(faker.number().randomDouble(2, 0, 10000)))
                // For client, you should create a method that generates a random client or fetches one from your database.
                // For simplicity sake, I'm just creating a new client here. You should replace this with your actual logic.

                .build();
    }
}
