package ph.cdo.backend.service.impl;

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
import ph.cdo.backend.entity.user.Admin;
import ph.cdo.backend.entity.user.Agent;
import ph.cdo.backend.enums.Role;
import ph.cdo.backend.errors.EntityDoesNotExistsException;
import ph.cdo.backend.errors.NullEntityException;
import ph.cdo.backend.repository.UserRepository;
import ph.cdo.backend.service.AdminService;
import ph.cdo.backend.service.IUserService;

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

    private UserRepository<Admin> userRepository;

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

        Admin savedUser = userService.save(testUser);

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

        Admin retrievedUser = userService.retrieve(testUser.getId());

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

        List<Admin> users = userService.retrieve();

        assertEquals(1, users.size());
        assertEquals(testUser, users.get(0));
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testUpdate() {
        when(userRepository.save(any())).thenReturn(testUser);

        testUser.setEmail("swaswa@gmail.com");
        Admin updatedUser = userService.update(testUser.getId(), testUser);

        assertEquals("swaswa@gmail.com", updatedUser.getEmail());
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

        List<Admin> users = userService.findAllEnabled();

        assertEquals(1, users.size());
        assertEquals(enabledUser, users.get(0));
        verify(userRepository, times(1)).findByIsEnabledTrue();
    }

    @Test
    public void testFindAllDisabled() {
        Admin disabledUser = createRandomAdmin();
        disabledUser.setEnabled(false);
        when(userRepository.findByIsEnabledFalse()).thenReturn(Collections.singletonList(disabledUser));

        List<Admin> users = userService.findAllDisabled();

        assertEquals(1, users.size());
        assertEquals(disabledUser, users.get(0));
        verify(userRepository, times(1)).findByIsEnabledFalse();
    }

    @Test
    public void testFindAllLocked() {
        Admin lockedUser = createRandomAdmin();
        lockedUser.setLocked(true);
        when(userRepository.findByIsLockedTrue()).thenReturn(Collections.singletonList(lockedUser));

        List<Admin> users = userService.findAllLocked();

        assertEquals(1, users.size());
        assertEquals(lockedUser, users.get(0));
        verify(userRepository, times(1)).findByIsLockedTrue();
    }

    @Test
    public void testFindAllUnlocked() {
        Admin unlockedUser = createRandomAdmin();
        unlockedUser.setLocked(false);
        when(userRepository.findByIsLockedFalse()).thenReturn(Collections.singletonList(unlockedUser));

        List<Admin> users = userService.findAllUnlocked();

        assertEquals(1, users.size());
        assertEquals(unlockedUser, users.get(0));
        verify(userRepository, times(1)).findByIsLockedFalse();
    }

    @Test
    public void testFindAllByRole() {
        Role testRole = Role.Client;
        Admin clientUser = createRandomAdmin();
        clientUser.setRole(testRole);
        when(userRepository.findByRole(testRole)).thenReturn(Collections.singletonList(clientUser));

        List<Admin> users = userService.findAllByRole(testRole);

        assertEquals(1, users.size());
        assertEquals(clientUser, users.get(0));
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
