package ph.cdo.backend.service.unitTests;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ph.cdo.backend.dto.records.TransactionDTO;
import ph.cdo.backend.dto.mapper.base.TransactionDTOMapper;
import ph.cdo.backend.entity.Transaction;
import ph.cdo.backend.enums.TransactionType;
import ph.cdo.backend.entity.user.Client;
import ph.cdo.backend.enums.Role;
import ph.cdo.backend.exceptions.EmptyListException;
import ph.cdo.backend.exceptions.EntityDoesNotExistsException;
import ph.cdo.backend.exceptions.InvalidValueException;
import ph.cdo.backend.repository.ClientRepository;
import ph.cdo.backend.repository.TransactionRepository;
import ph.cdo.backend.service.impl.user.ClientServiceImpl;
import ph.cdo.backend.service.impl.TransactionServiceImpl;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransactionTest {
    @InjectMocks
    private TransactionServiceImpl transactionService;

    @InjectMocks
    private ClientServiceImpl clientService;
    private static final Faker faker = new Faker();
    @Mock
    private ClientRepository clientRepository;


    @Mock
    private TransactionDTOMapper transactionDTOMapper;

    @Mock
    private TransactionRepository transactionRepository;

    private Client client;

    List<TransactionDTO> transactionDTOS;
    private Long id;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        client = new Client();
        id = 1L;
        transactionDTOS = new ArrayList<>();

        // populate client with required data if needed
        System.out.println("Setting up the client and id for testing.");

        when(clientRepository.findById(id)).thenReturn(Optional.of(client));

//        when(transactionDTOMapper.apply(any(Transaction.class))).thenAnswer(invocation -> {
//            Transaction input = invocation.getArgument(0);
//            TransactionDTO output = new TransactionDTO(input.getId(), input.getCreateDate(), input.getTransactionType(), input.getValue(), input.getClient().getEmail());
//            // copy properties from input to output as your mapping logic
//            return output;
//        });

        // You could change it to this:
        doAnswer(invocation -> {
            Transaction input = invocation.getArgument(0);
            TransactionDTO output = new TransactionDTO(input.getId(), input.getCreateDate(), input.getTransactionType(), input.getValue(), input.getClient().getEmail());
            // copy properties from input to output as per your mapping logic
            return output;
        }).when(transactionDTOMapper).apply(any(Transaction.class));
        System.out.println("Mocked findById method of ClientRepository.");
    }

    @Test
    void testFindAllByClientID() {
        List<Transaction> transactions = new ArrayList<>();

        // populate transactions list with mock transactions related to client
        for (int i = 0; i < 5; i++) {
            Transaction transaction = createRandomTransaction();
            transaction.getClient().setId(id);
            transactions.add(transaction);
        }
        when(transactionService.findAll()).thenReturn(transactionDTOS);
        System.out.println("Mocked transactions list.");
        // Mock the response from transactionRepository
        when(transactionRepository.findAll()).thenReturn(transactions);
        // Convert Transactions to TransactionDTOs
        transactionDTOS = transactions.stream()
                .map(transactionDTOMapper)
                .toList();

        // Simulate the findAll method

        System.out.println("Mocked findAll method of TransactionService.");

        List<TransactionDTO> result = transactionService.findAllByClientID(id);


        verify(clientRepository, times(1)).findById(id);

        // Assert that the correct number of transactions were returned
        assertEquals(transactions.size(), result.size());
        System.out.println("Assertion passed for testFindAllByClientID.");
    }

    @Test
    void testFindAllByClientIDWithInvalidId() {
        when(clientRepository.findById(id)).thenReturn(Optional.empty());
        System.out.println("Mocked findById method of ClientRepository to return Optional.empty().");

        assertThrows(EntityDoesNotExistsException.class, () -> transactionService.findAllByClientID(id));
        System.out.println("Assertion passed for testFindAllByClientIDWithInvalidId.");

        verify(clientRepository, times(1)).findById(id);
    }

    @Test
    void testFindAllByClientIDWithEmptyList() {
        // Simulate the findAll method returning an empty list
        when(transactionService.findAll()).thenReturn(new ArrayList<>());
        System.out.println("Mocked findAll method of TransactionService to return an empty list.");

        assertThrows(EmptyListException.class, () -> transactionService.findAllByClientID(id));
        System.out.println("Assertion passed for testFindAllByClientIDWithEmptyList.");

        verify(clientRepository, times(1)).findById(id);
    }

    public static Client createRandomClient() {
        return Client.builder()
                .role(Role.Client)
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .mobilePhone(faker.phoneNumber().cellPhone())
                .balance(BigDecimal.valueOf(faker.number().randomDouble(2, 0, 10000)))
                .build();
    }


    public static Transaction createRandomTransaction() {
        Faker faker = new Faker();

        return Transaction.builder()
                .transactionType(TransactionType.values()[faker.number().numberBetween(0, TransactionType.values().length)])
                .value(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 10000)))
                .createDate(faker.date().past(10, TimeUnit.DAYS))
                // For client, you should create a method that generates a random client or fetches one from your database.
                // For simplicity sake, I'm just creating a new client here. You should replace this with your actual logic.
                .client(createRandomClient())
                .build();
    }


    //////////
    @Test
    void testFindByMonth() {
        List<Transaction> transactions = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Transaction transaction =createRandomTransaction();
            LocalDate localDate = LocalDate.of(2023, 6, i+1);
            Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
            Date date = Date.from(instant);
            transaction.setCreateDate(date);
            transactions.add(transaction);
        }
        // Mock the response from transactionRepository
        when(transactionRepository.findAll()).thenReturn(transactions);
        // Convert Transactions to TransactionDTOs
        transactionDTOS = transactions.stream()
                .map(transactionDTOMapper)
                .toList();
//        when(transactionService.findAll()).thenReturn(transactionDTOS);

        List<TransactionDTO> result = transactionService.findByMonth(6, 2023);

        assertEquals(5, result.size());
    }

    @Test
    void testFindByMonthWithEmptyList() {
        when(transactionService.findAll()).thenReturn(new ArrayList<>());

        assertThrows(EmptyListException.class, () -> transactionService.findByMonth(6, 2023));
    }


    @Test
    void testFindByMonthWithInvalidMonth() {
        // Month is less than 1
        assertThrows(InvalidValueException.class, () -> transactionService.findByMonth(0, 2023));

        // Month is more than 12
        assertThrows(InvalidValueException.class, () -> transactionService.findByMonth(13, 2023));
    }

    @Test
    void testFindByMonthWithInvalidYear() {
        // Year is less than 1
        assertThrows(InvalidValueException.class, () -> transactionService.findByMonth(6, 0));
    }




    @Test
    void testFindByRangeDate() throws EmptyListException, InvalidValueException {
        // Dates for testing
        Date startDate = java.sql.Date.valueOf(LocalDate.of(2023, 1, 1));
        Date endDate = java.sql.Date.valueOf(LocalDate.of(2023, 12, 31));

        // Mocked transactions
        Transaction transaction1 = createRandomTransaction();
        transaction1.setCreateDate(java.sql.Date.valueOf(LocalDate.of(2023, 6, 15)));

        Transaction transaction2 = createRandomTransaction();
        transaction2.setCreateDate(java.sql.Date.valueOf(LocalDate.of(2023, 7, 15)));

        // Mocked transaction DTOs
        TransactionDTO transactionDTO1 = transactionDTOMapper.apply(transaction1);
        TransactionDTO transactionDTO2 = transactionDTOMapper.apply(transaction2);

        List<TransactionDTO> transactionDTOS = Arrays.asList(transactionDTO1, transactionDTO2);

        // Mock the response from transactionRepository
        when(transactionRepository.findAll()).thenReturn(Arrays.asList(transaction1, transaction2));

        List<TransactionDTO> transactions = transactionService.findByRangeDate(startDate, endDate);

        // Assert that the transactions list contains the correct number of transactions
        assertEquals(2, transactions.size());
    }

    @Test
    void testFindByRangeDateWithInvalidDates() {
        Date startDate = java.sql.Date.valueOf(LocalDate.of(2023, 12, 31));
        Date endDate = java.sql.Date.valueOf(LocalDate.of(2023, 1, 1));

        assertThrows(InvalidValueException.class, () -> transactionService.findByRangeDate(startDate, endDate));
    }

    @Test
    void testFindByRangeDateWithEmptyList() throws InvalidValueException {
        Date startDate = java.sql.Date.valueOf(LocalDate.of(2023, 1, 1));
        Date endDate = java.sql.Date.valueOf(LocalDate.of(2023, 12, 31));

        when(transactionRepository.findAll()).thenReturn(List.of());

        assertThrows(EmptyListException.class, () -> transactionService.findByRangeDate(startDate, endDate));
    }









    @Test
    void testFindByID() throws EntityDoesNotExistsException {
        // Mocked transaction
        Transaction transaction = createRandomTransaction();
        transaction.setId(1L);

        // Mock the response from transactionRepository
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));

        TransactionDTO foundTransaction = transactionService.findByID(1L);

        // Assert that the correct transaction was returned
        assertEquals(1L, foundTransaction.id());
    }

    @Test
    void testFindByIDWithNonExistingID() {
        // Mock the response from transactionRepository
        when(transactionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityDoesNotExistsException.class, () -> transactionService.findByID(1L));
    }


    @Test
    void testFindByType() throws EmptyListException {
        TransactionType transactionType = TransactionType.DEBIT; // Or your defined transaction type
        List<Transaction> transactions = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Transaction transaction = createRandomTransaction();
            transaction.setTransactionType(transactionType);
            transactions.add(transaction);
        }

        // Mock the response from transactionRepository
        when(transactionRepository.findAll()).thenReturn(transactions);
        // Convert Transactions to TransactionDTOs
        transactionDTOS = transactions.stream()
                .map(transactionDTOMapper)
                .toList();

//        // Mock the findAll method
//        when(transactionService.findAll()).thenReturn(transactionDTOS);

        List<TransactionDTO> result = transactionService.findByType(transactionType);

        // Assert that the correct number of transactions were returned
        assertEquals(transactions.size(), result.size());
    }

    @Test
    void testFindByTypeWithEmptyList() {
        TransactionType transactionType = TransactionType.DEBIT; // Or your defined transaction type

        // Simulate the findAll method returning an empty list
        when(transactionService.findAll()).thenReturn(new ArrayList<>());

        assertThrows(EmptyListException.class, () -> transactionService.findByType(transactionType));
    }


    @Test
    void testSortedList() {
        // Create a list of transactions with different IDs
        List<Transaction> transactions = new ArrayList<>();
        for (int i = 5; i > 0; i--) {
            Transaction transaction = new Transaction();
            transaction.setId(Long.valueOf(i));

        }
        // Mock the response from transactionRepository
        when(transactionRepository.findAll()).thenReturn(transactions);
        // Convert Transactions to TransactionDTOs
        transactionDTOS = transactions.stream()
                .map(transactionDTOMapper)
                .toList();
        // Sort the list using the sortedList method
        List<TransactionDTO> sortedTransactions = transactionService.sortedList(transactionDTOS, Comparator.comparing(TransactionDTO::id));

        // Assert that the transactions are sorted correctly
        for (int i = 0; i < sortedTransactions.size(); i++) {
            assertEquals(Long.valueOf(i + 1), sortedTransactions.get(i).id());
        }
    }




    @Test
    void testDeleteTransactionByIdNullId() {
        Long id = null;

        assertThrows(InvalidValueException.class, () -> transactionService.deleteTransactionById(id));

        verify(transactionRepository, times(0)).findById(anyLong());
    }

    @Test
    void testDeleteTransactionByIdInvalidId() {
        Long id = -1L;

        assertThrows(InvalidValueException.class, () -> transactionService.deleteTransactionById(id));

        verify(transactionRepository, times(0)).findById(anyLong());
    }

    @Test
    void testDeleteTransactionByIdEntityDoesNotExist() {
        Long id = 1L;
        when(transactionRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityDoesNotExistsException.class, () -> transactionService.deleteTransactionById(id));

        verify(transactionRepository, times(1)).findById(id);
    }


    //TODO FIX THIS TEST CASE!!!
//    @Test
//    void testDeleteTransactionById() throws NullEntityException, EntityDoesNotExistsException {
//        Transaction transaction = new Transaction();
//        transaction.setId(1L);
//
//        // Mock the behavior of the transaction repository
//        when(transactionRepository.findById(id)).thenReturn(Optional.of(transaction));
//        doNothing().when(transactionRepository).delete(transaction);
//        when(transactionRepository.findById(id)).thenReturn(Optional.empty());
//
//        // Mock the behavior of the client entity
//        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));
//
//        // Delete the transaction
//        transactionService.deleteTransactionById(id);
//
//        // Verify the interactions with the transaction repository
//        verify(transactionRepository, times(2)).findById(id);
//        verify(transactionRepository, times(1)).delete(transaction);
//
//        // Verify the interactions with the client repository
//        verify(clientService, times(1)).deleteById(1L);
//
//    }
}
