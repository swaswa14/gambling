package ph.cdo.backend.service.unitTests;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ph.cdo.backend.dto.records.ClientDTOEntity;
import ph.cdo.backend.dto.mapper.impl.ClientDTOMapper;
import ph.cdo.backend.entity.Transaction;
import ph.cdo.backend.enums.TransactionType;
import ph.cdo.backend.entity.user.Client;
import ph.cdo.backend.enums.Role;
import ph.cdo.backend.exceptions.EntityDoesNotExistsException;
import ph.cdo.backend.exceptions.NullEntityException;
import ph.cdo.backend.repository.ClientRepository;
import ph.cdo.backend.repository.TransactionRepository;
import ph.cdo.backend.service.ClientService;
import ph.cdo.backend.service.TransactionService;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class IUserClientServiceTest {

    @Autowired
    @Qualifier("ClientService")
    private ClientService userService;

    @Autowired
    private TransactionService transactionService;

    @MockBean
    private TransactionRepository transactionRepository;

    @MockBean
    private ClientDTOMapper clientDTOMapper;

    private static final Faker faker = new Faker();

    @MockBean

    private ClientRepository userRepository;

    private Client testUser;

    @BeforeEach
    public void setUp() {
        testUser = createRandomClient();
        testUser.setId(1L);
    }

    @AfterEach void reset(){
        userRepository.deleteAll();
        transactionRepository.deleteAll();
    }

    // ... existing tests ...

    @Test
    public void testSave() {
        when(userRepository.save(any())).thenReturn(testUser);

        ClientDTOEntity savedUser = userService.save(testUser);

        assertEquals(clientDTOMapper.apply(testUser), savedUser);
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    public void testSaveNull() {
        assertThrows(NullEntityException.class, () -> userService.save(null));
    }

    @Test
    public void testRetrieveById() {
        when(userRepository.save(any())).thenReturn(testUser);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testUser));

        ClientDTOEntity retrievedUser = userService.retrieve(testUser.getId());

        assertEquals(clientDTOMapper.apply(testUser), retrievedUser);
        verify(userRepository, times(1)).findById(testUser.getId());
    }

    @Test
    public void testRetrieveByIdNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityDoesNotExistsException.class, () -> userService.retrieve(testUser.getId()));
    }

    @Test
    public void testRetrieveAll() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(testUser));

        List<ClientDTOEntity> users = userService.retrieve();

        assertEquals(1, users.size());
        assertEquals(clientDTOMapper.apply(testUser), users.get(0));
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testUpdate() {
        when(userRepository.findById(any())).thenReturn(Optional.of(testUser));
        when(userRepository.save(testUser)).thenReturn(testUser);
        ClientDTOEntity mockClientDTO = new ClientDTOEntity(
                1L, // id
                Role.Client, // role
                "swaswa@gmail.com", // email
                "Default Phone", // phone
                new BigDecimal("20000.00")

        );

        when(clientDTOMapper.apply(any(Client.class))).thenReturn(mockClientDTO);

        testUser.setEmail("swaswa@gmail.com");
        assertEquals("swaswa@gmail.com", testUser.getEmail());
        ClientDTOEntity updatedUser = userService.update(testUser.getId(), testUser);

        when(clientDTOMapper.apply(any(Client.class))).thenReturn(updatedUser);

        assertEquals("swaswa@gmail.com", updatedUser.email());
        verify(userRepository, times(1)).save(testUser);

    }

    @Test
    public void testUpdateNullId() {
        Client userWithoutId = createRandomClient();

        assertThrows(EntityDoesNotExistsException.class, () -> userService.update(1L, userWithoutId));
    }

    @Test
    public void testFindAllEnabled() {
        Client enabledUser = createRandomClient();
        enabledUser.setEnabled(true);
        when(userRepository.findByIsEnabledTrue()).thenReturn(Collections.singletonList(enabledUser));

        List<ClientDTOEntity> users = userService.findAllEnabled();

        assertEquals(1, users.size());
        assertEquals(clientDTOMapper.apply(enabledUser), users.get(0));
        verify(userRepository, times(1)).findByIsEnabledTrue();
    }

    @Test
    public void testFindAllDisabled() {
        Client disabledUser = createRandomClient();
        disabledUser.setEnabled(false);
        when(userRepository.findByIsEnabledFalse()).thenReturn(Collections.singletonList(disabledUser));

        List<ClientDTOEntity> users = userService.findAllDisabled();

        assertEquals(1, users.size());
        assertEquals(clientDTOMapper.apply(disabledUser), users.get(0));
        verify(userRepository, times(1)).findByIsEnabledFalse();
    }

    @Test
    public void testFindAllLocked() {
        Client lockedUser = createRandomClient();
        lockedUser.setLocked(true);
        when(userRepository.findByIsLockedTrue()).thenReturn(Collections.singletonList(lockedUser));

        List<ClientDTOEntity> users = userService.findAllLocked();
        System.out.println(users.toString());
        assertEquals(1, users.size());
        assertEquals(clientDTOMapper.apply(lockedUser), users.get(0));
        verify(userRepository, times(1)).findByIsLockedTrue();
    }

    @Test
    public void testFindAllUnlocked() {
        Client unlockedUser = createRandomClient();
        unlockedUser.setLocked(false);
        when(userRepository.findByIsLockedFalse()).thenReturn(Collections.singletonList(unlockedUser));

        List<ClientDTOEntity> users = userService.findAllUnlocked();

        assertEquals(1, users.size());
        assertEquals(clientDTOMapper.apply(unlockedUser), users.get(0));
        verify(userRepository, times(1)).findByIsLockedFalse();
    }

    @Test
    public void testFindAllByRole() {
        Role testRole = Role.Client;
        Client clientUser = createRandomClient();
        clientUser.setRole(testRole);
        when(userRepository.findByRole(testRole)).thenReturn(Collections.singletonList(clientUser));

        List<ClientDTOEntity> users = userService.findAllByRole(testRole);

        assertEquals(1, users.size());
        assertEquals(clientDTOMapper.apply(clientUser), users.get(0));
        verify(userRepository, times(1)).findByRole(testRole);
    }


    @Test
    void testAddTransaction() {
        Transaction transaction = createRandomTransaction();
        when(userRepository.save(testUser)).thenReturn(testUser);
        Long id = 1L;




       userService.addTransaction(testUser, transaction);
        Assertions.assertEquals(1, testUser.getTransactions().size());

//        verify(userRepository, times(1)).findById(id);
//        verify(userRepository, times(1)).save(testUser);

        // Assert that transaction was added to the client
        assertTrue(testUser.getTransactions().contains(transaction));
    }

    @Test
    public void testIsEmailTaken() {
        // Arrange
        String email = "test@test.com";
        when(userRepository.findAllByEmail(email.toLowerCase()))
                .thenReturn(Collections.singletonList(createRandomClient())); // Assuming User is your entity class

        // Act
        boolean isEmailTaken = userService.isEmailTaken(email);

        // Assert
        assertTrue(isEmailTaken, "Expected email to be taken");
    }

    @Test
    public void testIsEmailNotTaken() {
        // Arrange
        String email = "notexist@test.com";
        when(userRepository.findAllByEmail(email.toLowerCase()))
                .thenReturn(List.of());

        // Act
        boolean isEmailTaken = userService.isEmailTaken(email);

        // Assert
        assertFalse(isEmailTaken, "Expected email to not be taken");
    }

    @Test
    void testAddTransactionWithInvalidId() {
        Transaction transaction = createRandomTransaction();

        Long id = 1L;

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityDoesNotExistsException.class, () -> userService.addTransaction(id, transaction));
//
//        verify(userRepository, times(1)).findById(id);
        verify(userRepository, never()).save(any(Client.class));
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
                // For client, you should create a method that generates a random client or fetches one from your database.
                // For simplicity sake, I'm just creating a new client here. You should replace this with your actual logic.
                .build();
    }



}