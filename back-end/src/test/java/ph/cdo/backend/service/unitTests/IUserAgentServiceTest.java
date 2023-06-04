package ph.cdo.backend.service.unitTests;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ph.cdo.backend.entity.user.Agent;
import ph.cdo.backend.entity.user.Client;
import ph.cdo.backend.enums.Role;
import ph.cdo.backend.errors.EntityDoesNotExistsException;
import ph.cdo.backend.errors.NullEntityException;
import ph.cdo.backend.repository.AgentRepository;
import ph.cdo.backend.repository.UserRepository;
import ph.cdo.backend.service.AgentService;
import ph.cdo.backend.service.IUserService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
@SpringBootTest
@ActiveProfiles("test")
public class IUserAgentServiceTest {

    @Autowired
    @Qualifier("AgentService")
    private AgentService userService;


    private static final Faker faker = new Faker();

    @MockBean

    private AgentRepository userRepository;

    private Agent testUser;

    @BeforeEach
    public void setUp() {
        testUser = createRandomAgent();
        testUser.setId(1L);
    }

    // ... existing tests ...

    @Test
    public void testSave() {
        when(userRepository.save(any())).thenReturn(testUser);

        Agent savedUser = userService.save(testUser);

        assertEquals(testUser, savedUser);
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

        Agent retrievedUser = userService.retrieve(testUser.getId());

        assertEquals(testUser, retrievedUser);
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

        List<Agent> users = userService.retrieve();

        assertEquals(1, users.size());
        assertEquals(testUser, users.get(0));
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testUpdate() {
        when(userRepository.save(any())).thenReturn(testUser);

        testUser.setEmail("swaswa@gmail.com");
        Agent updatedUser = userService.update(testUser.getId(), testUser);

        assertEquals("swaswa@gmail.com", updatedUser.getEmail());
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    public void testUpdateNullId() {
        Agent userWithoutId = createRandomAgent();

        assertThrows(EntityDoesNotExistsException.class, () -> userService.update(1L, userWithoutId));
    }


    @Test
    public void testFindAllEnabled() {
        Agent enabledUser = createRandomAgent();
        enabledUser.setEnabled(true);
        when(userRepository.findByIsEnabledTrue()).thenReturn(Collections.singletonList(enabledUser));

        List<Agent> users = userService.findAllEnabled();

        assertEquals(1, users.size());
        assertEquals(enabledUser, users.get(0));
        verify(userRepository, times(1)).findByIsEnabledTrue();
    }

    @Test
    public void testFindAllDisabled() {
        Agent disabledUser = createRandomAgent();
        disabledUser.setEnabled(false);
        when(userRepository.findByIsEnabledFalse()).thenReturn(Collections.singletonList(disabledUser));

        List<Agent> users = userService.findAllDisabled();

        assertEquals(1, users.size());
        assertEquals(disabledUser, users.get(0));
        verify(userRepository, times(1)).findByIsEnabledFalse();
    }

    @Test
    public void testFindAllLocked() {
        Agent lockedUser = createRandomAgent();
        lockedUser.setLocked(true);
        when(userRepository.findByIsLockedTrue()).thenReturn(Collections.singletonList(lockedUser));

        List<Agent> users = userService.findAllLocked();

        assertEquals(1, users.size());
        assertEquals(lockedUser, users.get(0));
        verify(userRepository, times(1)).findByIsLockedTrue();
    }

    @Test
    public void testFindAllUnlocked() {
        Agent unlockedUser = createRandomAgent();
        unlockedUser.setLocked(false);
        when(userRepository.findByIsLockedFalse()).thenReturn(Collections.singletonList(unlockedUser));

        List<Agent> users = userService.findAllUnlocked();

        assertEquals(1, users.size());
        assertEquals(unlockedUser, users.get(0));
        verify(userRepository, times(1)).findByIsLockedFalse();
    }

    @Test
    public void testFindAllByRole() {
        Role testRole = Role.Client;
        Agent clientUser = createRandomAgent();
        clientUser.setRole(testRole);
        when(userRepository.findByRole(testRole)).thenReturn(Collections.singletonList(clientUser));

        List<Agent> users = userService.findAllByRole(testRole);

        assertEquals(1, users.size());
        assertEquals(clientUser, users.get(0));
        verify(userRepository, times(1)).findByRole(testRole);
    }


    public static Agent createRandomAgent(){
        return Agent.builder()
                .role(Role.Agent)
                .agentCode(faker.number().digits(4))
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .mobilePhone(faker.phoneNumber().cellPhone())
                .build();
    }
}
