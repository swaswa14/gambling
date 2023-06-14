package ph.jsalcedo.backendv2.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ph.jsalcedo.backendv2.dto.user.TransactionDto;
import ph.jsalcedo.backendv2.entity.Transaction;
import ph.jsalcedo.backendv2.entity.user.details.ClientDetails;
import ph.jsalcedo.backendv2.model.TransactionType;
import ph.jsalcedo.backendv2.repository.ClientDetailsRepository;
import ph.jsalcedo.backendv2.repository.TransactionRepository;
import ph.jsalcedo.backendv2.repository.UserRepository;

import java.math.BigDecimal;
import java.util.*;

import static ph.jsalcedo.backendv2.utility.EntityUtilityBuilder.*;
@SpringBootTest
@ActiveProfiles("test")
public class TransactionServiceTest {
    private final int PAGE = 0;
    private final int SIZE = 10;

    private final Random ran = new Random();

    private final TransactionService transactionService;
    private final ClientDetailsService clientDetailsService;
    private final UserRepository userRepository;
    private final ClientDetailsRepository clientDetailsRepository;

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceTest(TransactionService transactionService, ClientDetailsService clientDetailsService, UserRepository userRepository, ClientDetailsRepository clientDetailsRepository, TransactionRepository transactionRepository) {
        this.transactionService = transactionService;
        this.clientDetailsService = clientDetailsService;
        this.userRepository = userRepository;
        this.clientDetailsRepository = clientDetailsRepository;
        this.transactionRepository = transactionRepository;
    }


    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        clientDetailsRepository.deleteAll();
        transactionRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        clientDetailsRepository.deleteAll();
        transactionRepository.deleteAll();
    }

    @Test
    void testNotNull(){
        Assertions.assertNotNull(transactionService);
        Assertions.assertNotNull(clientDetailsService);
        Assertions.assertNotNull(userRepository);
        Assertions.assertNotNull(transactionRepository);
    }


    @Test
    void testFindAllByClient(){
        ClientDetails details = createRandomClient();
        details = clientDetailsRepository.save(details);

        Assertions.assertNotNull(details);

        Assertions.assertEquals(0, transactionRepository.findAll().size());

        for(int i = 0; i < 20; i++){
            clientDetailsService.addTransaction(details.getId(), createRandomTransaction());

        }

        List<TransactionDto> transactionDtoList =  transactionService.findAllByClient(details, PAGE, SIZE);

        Assertions.assertEquals(10, transactionDtoList.size());
        Assertions.assertEquals(20, transactionRepository.findAll().size());
    }


    @Test
    void testFindAllByMonth(){
        ClientDetails details = createRandomClient();
        details = clientDetailsRepository.save(details);

        Assertions.assertNotNull(details);

        Assertions.assertEquals(0, transactionRepository.findAll().size());

        for(int i = 0; i < 20; i++){
            clientDetailsService.addTransaction(details.getId(), createRandomTransaction());

        }

        List<TransactionDto> transactionDtoList =  transactionService.findAllByClient(details, PAGE, SIZE);

        Assertions.assertEquals(10, transactionDtoList.size());
        Assertions.assertEquals(20, transactionRepository.findAll().size());


        for(int i = 0; i < 5; i++){
            clientDetailsService.addTransaction(details.getId(), createRandomTransaction(2020,6,15));
        }
        Assertions.assertEquals(25, transactionRepository.findAll().size());
        List<TransactionDto> dtoList = transactionService.findByMonth(6, 2020, PAGE,SIZE);

        transactionRepository.findAll().forEach(System.out::println);
        Assertions.assertNotNull(dtoList);
        Assertions.assertEquals(5, dtoList.size());


    }



    @Test
    void testFindByRangeAmount(){
        ClientDetails details = createRandomClient();
        details = clientDetailsRepository.save(details);

        Assertions.assertNotNull(details);

        Assertions.assertEquals(0, transactionRepository.findAll().size());

        for(int i = 0; i < 20; i++){
            Transaction transaction = createRandomTransaction();
            transaction.setAmount(BigDecimal.valueOf(1000L));
            clientDetailsService.addTransaction(details.getId(), transaction);
        }

        for(int i = 0; i < 5; i++){
            Transaction transaction = createRandomTransaction();
            transaction.setAmount(BigDecimal.valueOf(5000L));
            clientDetailsService.addTransaction(details.getId(), transaction);
        }

        Transaction transaction = createRandomTransaction();
        transaction.setAmount(BigDecimal.valueOf(6000L));
        clientDetailsService.addTransaction(details.getId(), transaction);



        Assertions.assertEquals(26, transactionRepository.findAll().size());

        List<TransactionDto> dtoList = transactionService.findByRangeAmount(4000L, 7000L, PAGE,SIZE);

        Assertions.assertNotNull(dtoList);

        Assertions.assertEquals(6, dtoList.size());
    }


    @Test
    void testFindByRangeDate(){
        // Create a client
        ClientDetails details = createRandomClient();
        details = clientDetailsRepository.save(details);

        // Assert that client is created
        Assertions.assertNotNull(details);

        // Check if the transactions repository is empty
        Assertions.assertEquals(0, transactionRepository.findAll().size());

        // Add 20 transactions
        for(int i = 0; i < 20; i++){
            clientDetailsService.addTransaction(details.getId(), createRandomTransaction());
        }

        // Check if all transactions are stored
        Assertions.assertEquals(20, transactionRepository.findAll().size());

        // Define date range for test
        Calendar cal = Calendar.getInstance();
        cal.set(2023, Calendar.JUNE, 1);
        Date startDate = cal.getTime();

        cal.set(2023, Calendar.JUNE, 30);
        Date endDate = cal.getTime();

        // Create 5 transactions within date range
        for(int i = 0; i < 5; i++){
            clientDetailsService.addTransaction(details.getId(), createRandomTransaction(2023,6,15));
        }

        // Check if all transactions are stored
        Assertions.assertEquals(25, transactionRepository.findAll().size());

        // Fetch transactions by date range
        List<TransactionDto> dtoList = transactionService.findByRangeDate(startDate, endDate, PAGE, SIZE);

        // Print all transactions for debugging
        transactionRepository.findAll().forEach(System.out::println);

        // Check if the fetched transactions are not null
        Assertions.assertNotNull(dtoList);

        // Check if the fetched transactions count is as expected
        Assertions.assertEquals(5, dtoList.size());
    }


    @Test
    void testFindByType(){
        // Create a client
        ClientDetails details = createRandomClient();
        details = clientDetailsRepository.save(details);

        // Assert that client is created
        Assertions.assertNotNull(details);

        // Check if the transactions repository is empty
        Assertions.assertEquals(0, transactionRepository.findAll().size());

        // Add 20 transactions of DEBIT type
        for(int i = 0; i < 20; i++){
            Transaction transaction = createRandomTransaction();
            transaction.setType(TransactionType.DEBIT);
            clientDetailsService.addTransaction(details.getId(), transaction);
        }

        // Check if all transactions are stored
        Assertions.assertEquals(20, transactionRepository.findAll().size());

        // Create 5 transactions of CREDIT type
        for(int i = 0; i < 5; i++){
            Transaction transaction = createRandomTransaction();
            transaction.setType(TransactionType.CREDIT);
            clientDetailsService.addTransaction(details.getId(), transaction);
        }

        // Check if all transactions are stored
        Assertions.assertEquals(25, transactionRepository.findAll().size());

        // Fetch transactions of CREDIT type
        List<TransactionDto> dtoList = transactionService.findByType(TransactionType.CREDIT, PAGE, SIZE);

        // Print all transactions for debugging
        transactionRepository.findAll().forEach(System.out::println);

        // Check if the fetched transactions are not null
        Assertions.assertNotNull(dtoList);

        // Check if the fetched transactions count is as expected
        Assertions.assertEquals(5, dtoList.size());
    }

    @Test
    void testDeleteTransactionById() {
        // Create a client
        ClientDetails details = createRandomClient();
        details = clientDetailsRepository.save(details);

        // Assert that client is created
        Assertions.assertNotNull(details);

        // Add a transaction
         clientDetailsService.addTransaction(details.getId(), createRandomTransaction());

         Optional<ClientDetails> details1 = clientDetailsRepository.findById(details.getId());

         Assertions.assertTrue(details1.isPresent());

         details = details1.get();


         List<TransactionDto> dtoList = transactionService.findAllByClient(details.getId(), PAGE,SIZE);
         Assertions.assertNotNull(dtoList);
         Assertions.assertTrue(dtoList.size() >= 1);
         Long transactionID = dtoList.get(0).getId();

        // Check if the transaction is stored
        Assertions.assertNotNull(transactionRepository.findById(transactionID));

        // Delete the transaction
        boolean isDeleted = transactionService.deleteTransactionById(transactionID);

        // Verify that the transaction is deleted
        Assertions.assertTrue(isDeleted);

        // Check if the transaction is removed
        Assertions.assertFalse(transactionRepository.findById(transactionID).isPresent());
    }
}
