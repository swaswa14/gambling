package ph.jsalcedo.backendv2.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ph.jsalcedo.backendv2.dto.user.details.ClientDetailsDto;
import ph.jsalcedo.backendv2.entity.user.User;
import ph.jsalcedo.backendv2.entity.user.details.ClientDetails;
import ph.jsalcedo.backendv2.exceptions.EntityDoesNotExistsException;
import ph.jsalcedo.backendv2.exceptions.NullEntityException;
import ph.jsalcedo.backendv2.repository.ClientDetailsRepository;
import ph.jsalcedo.backendv2.repository.UserRepository;

import java.util.Optional;

import static ph.jsalcedo.backendv2.utility.EntityUtilityBuilder.*;
@SpringBootTest
@ActiveProfiles("test")
public class AbstractDetailsServiceTest {

    private final ClientDetailsService service;


    private final ClientDetailsRepository repository;

    private final UserRepository userRepository;

    @Autowired
    public AbstractDetailsServiceTest(@Qualifier("ClientDetailsService")ClientDetailsService service, ClientDetailsRepository repository,
                                 UserRepository userRepository) {
        this.service = service;
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        userRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testNotNull(){
        Assertions.assertNotNull(service);
        Assertions.assertNotNull(repository);
        Assertions.assertNotNull(userRepository);
    }

    @Test
    public void testFindByEmail(){
        ClientDetails details = createRandomClient();
        details.getBasicInformation().setEmail("swaswa14@yopmail.com");

        repository.save(details);

        ClientDetailsDto dto = service.findByEmail("swaswa14@yopmail.com");

        Assertions.assertNotNull(dto);
    }

    @Test
    public void testFindByEmailWhenNull(){
        Assertions.assertThrows(EntityDoesNotExistsException.class, ()->{
            service.findByEmail(null);
        });
    }

    @Test
    public void testAddUser(){
        User user = createRandomUser();

      user =   userRepository.save(user);

        Optional<User> user1 = userRepository.findById(user.getId());

        Assertions.assertTrue(user1.isPresent());

        User savedUser = user1.get();

        ClientDetails details = createRandomClient();

       ClientDetailsDto dto =  service.addUser(details, savedUser);

       Assertions.assertNotNull(dto);

       Assertions.assertEquals(1, repository.findAll().size());
    }

    @Test
    public void testAddUserWhenUserNull(){
        Assertions.assertThrows(NullEntityException.class, ()->{
            ClientDetails details = createRandomClient();
            ClientDetailsDto dto =  service.addUser(details, null);
        });
    }
    @Test
    public void testFindByUser(){
        User user = createRandomUser();

        user =  userRepository.save(user);
        ClientDetails details = createRandomClient();
        Optional<User> user1 = userRepository.findById(user.getId());

        Assertions.assertTrue(user1.isPresent());

        User savedUser = user1.get();

        ClientDetailsDto dto =  service.addUser(details, savedUser);


        ClientDetailsDto foundDTO = service.findByUser(savedUser);

        Assertions.assertNotNull(foundDTO);
        Assertions.assertEquals(dto.getEmail(), foundDTO.getEmail());


    }


    @Test
    public void findByUserWhenNull(){
        Assertions.assertThrows(NullEntityException.class, ()->{
            service.findByUser(null);
        });
    }

    @Test
    public void findByUserId(){
        User user = createRandomUser();

        user = userRepository.save(user);
        ClientDetails details = createRandomClient();
        Optional<User> user1 = userRepository.findById(user.getId());

        Assertions.assertTrue(user1.isPresent());

        User savedUser = user1.get();

        ClientDetailsDto dto =  service.addUser(details, savedUser);


        Assertions.assertNotNull(dto);

        ClientDetailsDto foundDTO = service.findByUserId(savedUser.getId());

        Assertions.assertNotNull(foundDTO);

        Assertions.assertEquals(dto.getEmail(), foundDTO.getEmail());

    }

    @Test
    void findByUserWhenIdNull(){
        Assertions.assertThrows(NullEntityException.class, ()->{
            service.findByUserId(null);
        });
    }

    @Test
    void findByUserWhenIdDoesNotExists(){
        Assertions.assertThrows(EntityDoesNotExistsException.class, ()->{
           service.findByUserId(100L);
        });
    }
}
