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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import ph.cdo.backend.dto.records.AgentDTOEntity;
import ph.cdo.backend.dto.mapper.impl.user.AgentDTOMapper;
import ph.cdo.backend.entity.base.Name;
import ph.cdo.backend.entity.user.Agent;
import ph.cdo.backend.enums.Role;
import ph.cdo.backend.exceptions.EntityDoesNotExistsException;
import ph.cdo.backend.exceptions.NullEntityException;
import ph.cdo.backend.repository.AgentRepository;
import ph.cdo.backend.service.AgentService;

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

    @MockBean
    private AgentDTOMapper agentDTOMapper;

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

        AgentDTOEntity savedUser = userService.save(testUser);

        assertEquals(agentDTOMapper.apply(testUser), savedUser);
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

        AgentDTOEntity retrievedUser = userService.retrieve(testUser.getId());

        assertEquals(agentDTOMapper.apply(testUser), retrievedUser);
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

        List<AgentDTOEntity> users = userService.retrieve(1,1 ,"id");

        assertEquals(1, users.size());
        assertEquals(agentDTOMapper.apply(testUser), users.get(0));
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testUpdate() {
        when(userRepository.findById(any())).thenReturn(Optional.of(testUser));
        when(userRepository.save(testUser)).thenReturn(testUser);
        Name name = mock(Name.class);
        AgentDTOEntity mockAgentDTO = new AgentDTOEntity(
                1L, // id
                Role.Client, // role
                "swaswa@gmail.com", // email
                "Default Phone", // phone
                "Default Agent Code",
                name

        );

        when(agentDTOMapper.apply(any(Agent.class))).thenReturn(mockAgentDTO);

        testUser.setEmail("swaswa@gmail.com");
        assertEquals("swaswa@gmail.com", testUser.getEmail());
        AgentDTOEntity updatedUser = userService.update(testUser.getId(), testUser);

        when(agentDTOMapper.apply(any(Agent.class))).thenReturn(updatedUser);

        assertEquals("swaswa@gmail.com", updatedUser.email());
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
        when(userRepository.findAllByIsEnabledTrue(any(Pageable.class))).thenReturn(Collections.singletonList(enabledUser));

        List<AgentDTOEntity> users = userService.findAllEnabled(1,10,"id");

        assertEquals(1, users.size());
        assertEquals(agentDTOMapper.apply(enabledUser), users.get(0));
        verify(userRepository, times(1)).findAllByIsEnabledTrue(any(Pageable.class));
    }

    @Test
    public void testFindAllDisabled() {
        Agent disabledUser = createRandomAgent();
        disabledUser.setEnabled(false);
        when(userRepository.findAllByIsEnabledFalse(any(Pageable.class))).thenReturn(Collections.singletonList(disabledUser));

        List<AgentDTOEntity> users = userService.findAllDisabled(1,10,"id");

        assertEquals(1, users.size());
        assertEquals(agentDTOMapper.apply(disabledUser), users.get(0));
        verify(userRepository, times(1)).findAllByIsEnabledFalse(any(Pageable.class));
    }

    @Test
    public void testFindAllLocked() {
        Agent lockedUser = createRandomAgent();
        lockedUser.setLocked(true);
        when(userRepository.findAllByIsLockedTrue(any(Pageable.class))).thenReturn(Collections.singletonList(lockedUser));

        List<AgentDTOEntity> users = userService.findAllLocked(1,10,"id");

        assertEquals(1, users.size());
        assertEquals(agentDTOMapper.apply(lockedUser), users.get(0));
        verify(userRepository, times(1)).findAllByIsLockedTrue(any(Pageable.class));
    }

    @Test
    public void testFindAllUnlocked() {
        Agent unlockedUser = createRandomAgent();
        unlockedUser.setLocked(false);
        when(userRepository.findAllByIsLockedFalse(any(Pageable.class))).thenReturn(Collections.singletonList(unlockedUser));

        List<AgentDTOEntity> users = userService.findAllUnlocked(1,10,"id");

        assertEquals(1, users.size());
        assertEquals(agentDTOMapper.apply(unlockedUser), users.get(0));
        verify(userRepository, times(1)).findAllByIsLockedFalse(any(Pageable.class));
    }

    @Test
    public void testFindAllByRole() {
        // Setup
        Role testRole = Role.Client;
        Agent clientUser = createRandomAgent();
        clientUser.setRole(testRole);

        int page = 1;
        int size = 10;
        String field = "id";

        Pageable pageable = createPageable(page, size, field);
        when(userRepository.findAllByRole(eq(testRole), any(Pageable.class))).thenAnswer(invocation -> {
            Pageable receivedPageable = invocation.getArgument(1, Pageable.class);
            if (receivedPageable.equals(pageable)) {
                return Collections.singletonList(clientUser);
            } else {
                return Collections.emptyList();
            }
        });

        // Execute
        List<AgentDTOEntity> users = userService.findAllByRole(testRole, page, size, field);

        // Verify
        assertEquals(1, users.size());
        assertEquals(agentDTOMapper.apply(clientUser), users.get(0));
        verify(userRepository, times(1)).findAllByRole(testRole, pageable);
    }

    private static Pageable createPageable(int page, int size, String field){
        return PageRequest.of(page, size, Sort.by(field).and(Sort.by("id").descending()));
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
