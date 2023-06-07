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
import ph.cdo.backend.dto.records.AdminDTOEntity;
import ph.cdo.backend.dto.mapper.impl.AdminDTOMapper;
import ph.cdo.backend.entity.user.Admin;
import ph.cdo.backend.enums.Role;
import ph.cdo.backend.exceptions.EntityDoesNotExistsException;
import ph.cdo.backend.exceptions.NullEntityException;
import ph.cdo.backend.repository.AdminRepository;
import ph.cdo.backend.service.AdminService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class IUserAdminServiceTest {


    @Autowired
    @Qualifier("AdminService")
    private AdminService userService;


    private static final Faker faker = new Faker();

    @MockBean
    private AdminRepository userRepository;

    @MockBean
    private AdminDTOMapper adminDTOMapper;

    private Admin testUser;

    @BeforeEach
    public void setUp() {
        testUser = createRandomAdmin();
        testUser.setId(1L);
    }

    // ... existing tests ...

    @Test
    public void testSave() {
        when(userRepository.save(any())).thenReturn(testUser);

        AdminDTOEntity savedUser = userService.save(testUser);

        assertEquals(adminDTOMapper.apply(testUser), savedUser);
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

        AdminDTOEntity retrievedUser = userService.retrieve(testUser.getId());

        assertEquals(adminDTOMapper.apply(testUser), retrievedUser);
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

        List<AdminDTOEntity> users = userService.retrieve();

        assertEquals(1, users.size());
        assertEquals(adminDTOMapper.apply(testUser), users.get(0));
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testUpdate() {
        when(userRepository.findById(any())).thenReturn(Optional.of(testUser));
        when(userRepository.save(testUser)).thenReturn(testUser);
        AdminDTOEntity mockAdminDTO = new AdminDTOEntity(
                1L, // id
                Role.Client, // role
                "swaswa@gmail.com", // email
                "Default Name", // name
                "Default Address", // address
                "Default Phone" // phone
        );

        when(adminDTOMapper.apply(any(Admin.class))).thenReturn(mockAdminDTO);

        testUser.setEmail("swaswa@gmail.com");
        assertEquals("swaswa@gmail.com", testUser.getEmail());
        AdminDTOEntity updatedUser = userService.update(testUser.getId(), testUser);

        when(adminDTOMapper.apply(any(Admin.class))).thenReturn(updatedUser);

        assertEquals("swaswa@gmail.com", updatedUser.email());
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    public void testUpdateNullId() {
        Admin userWithoutId = createRandomAdmin();

        assertThrows(EntityDoesNotExistsException.class, () -> userService.update(1L, userWithoutId));
    }


    @Test
    public void testFindAllEnabled() {
        Admin enabledUser = createRandomAdmin();
        enabledUser.setEnabled(true);
        when(userRepository.findByIsEnabledTrue()).thenReturn(Collections.singletonList(enabledUser));

        List<AdminDTOEntity> users = userService.findAllEnabled();

        assertEquals(1, users.size());
        assertEquals(adminDTOMapper.apply(enabledUser), users.get(0));
        verify(userRepository, times(1)).findByIsEnabledTrue();
    }

    @Test
    public void testFindAllDisabled() {
        Admin disabledUser = createRandomAdmin();
        disabledUser.setEnabled(false);
        when(userRepository.findByIsEnabledFalse()).thenReturn(Collections.singletonList(disabledUser));

        List<AdminDTOEntity> users = userService.findAllDisabled();

        assertEquals(1, users.size());
        assertEquals(adminDTOMapper.apply(disabledUser), users.get(0));
        verify(userRepository, times(1)).findByIsEnabledFalse();
    }

    @Test
    public void testFindAllLocked() {
        Admin lockedUser = createRandomAdmin();
        lockedUser.setLocked(true);
        when(userRepository.findByIsLockedTrue()).thenReturn(Collections.singletonList(lockedUser));

        List<AdminDTOEntity> users = userService.findAllLocked();

        assertEquals(1, users.size());
        assertEquals(adminDTOMapper.apply(lockedUser), users.get(0));
        verify(userRepository, times(1)).findByIsLockedTrue();
    }

    @Test
    public void testFindAllUnlocked() {
        Admin unlockedUser = createRandomAdmin();
        unlockedUser.setLocked(false);
        when(userRepository.findByIsLockedFalse()).thenReturn(Collections.singletonList(unlockedUser));

        List<AdminDTOEntity> users = userService.findAllUnlocked();

        assertEquals(1, users.size());
        assertEquals(adminDTOMapper.apply(unlockedUser), users.get(0));
        verify(userRepository, times(1)).findByIsLockedFalse();
    }

    @Test
    public void testFindAllByRole() {
        Role testRole = Role.Client;
        Admin clientUser = createRandomAdmin();
        clientUser.setRole(testRole);
        when(userRepository.findByRole(testRole)).thenReturn(Collections.singletonList(clientUser));

        List<AdminDTOEntity> users = userService.findAllByRole(testRole);

        assertEquals(1, users.size());
        assertEquals(adminDTOMapper.apply(clientUser), users.get(0));
        verify(userRepository, times(1)).findByRole(testRole);
    }



    public static Admin createRandomAdmin(){
        return Admin.builder()
                .role(Role.Admin)
                .name(faker.company().profession())
                .name(faker.name().firstName())
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .mobilePhone(faker.phoneNumber().cellPhone())
                .build();
    }
}
