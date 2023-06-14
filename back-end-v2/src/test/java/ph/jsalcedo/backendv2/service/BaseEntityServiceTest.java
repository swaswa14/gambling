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
import ph.jsalcedo.backendv2.entity.user.details.ClientDetails;
import ph.jsalcedo.backendv2.exceptions.EntityDoesNotExistsException;
import ph.jsalcedo.backendv2.exceptions.NullEntityException;
import ph.jsalcedo.backendv2.repository.ClientDetailsRepository;
import ph.jsalcedo.backendv2.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static ph.jsalcedo.backendv2.utility.EntityUtilityBuilder.*;


@SpringBootTest
@ActiveProfiles("test")
public class BaseEntityServiceTest {
  private final ClientDetailsService service;


  private final ClientDetailsRepository repository;

  private final UserRepository userRepository;

  @Autowired
    public BaseEntityServiceTest(@Qualifier("ClientDetailsService")ClientDetailsService service, ClientDetailsRepository repository,
                                 UserRepository userRepository) {
        this.service = service;
        this.repository = repository;
      this.userRepository = userRepository;
  }

    @BeforeEach
    void setUp() {

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
    }

    @Test
    public void testingCreate(){
        ClientDetails clientDetails = createRandomClient();

        ClientDetailsDto dto = service.create(clientDetails);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(1, repository.findAll().size());
    }

    @Test
    public void shouldThrowNullEntityException(){
      Assertions.assertThrows(NullEntityException.class, ()->{
        service.create(null);
      });

    }


    @Test
    void whenDeleteShouldReturnTrue(){
        ClientDetails clientDetails = createRandomClient();

        ClientDetailsDto dto = service.create(clientDetails);

        boolean result = service.deleteEntity(dto.getId());

        Assertions.assertTrue(result);
    }

    @Test
    void whenFindingEntityThatdoesNotExistsShouldThrow(){
      Assertions.assertThrows(EntityDoesNotExistsException.class, ()->{
         service.findById(100L);
      });
    }

    @Test
    void testingFindByIdShouldThrowDTO(){
      ClientDetails details = createRandomClient();
        details = repository.save(details);
      ClientDetailsDto dto =  service.findById(details.getId());

      Assertions.assertNotNull(dto);
    }


    @Test
    void testingUpdateEntity(){
        ClientDetails details = createRandomClient();
        details = repository.save(details);

        details.getBasicInformation().setEmail("swaswa14@yopmail.com");

        ClientDetailsDto dto = service.updateEntity(details.getId(), details);
        Optional<ClientDetails> details1 = repository.findById(details.getId());


        Assertions.assertNotNull(dto);
        Assertions.assertTrue(details1.isPresent());

        Assertions.assertTrue("swaswa14@yopmail.com".equalsIgnoreCase( dto.getEmail()));

        Assertions.assertTrue("swaswa14@yopmail.com".equalsIgnoreCase(details1.get().getBasicInformation().getEmail()));
    }


    @Test
    void testFindAll(){
       for(int i = 0; i < 11; i++){
           repository.save(createRandomClient());
       }

        List<ClientDetailsDto> list = service.findAll(0, 10, "id");

       Assertions.assertNotNull(list);
       Assertions.assertEquals(10, list.size());
       Assertions.assertEquals(11, repository.findAll().size());
    }

    @Test
    void testFindByDTO(){
        ClientDetails details = createRandomClient();
        details=  repository.save(details);

        ClientDetailsDto dto = service.findById(details.getId());

        Assertions.assertNotNull(dto);

        ClientDetails details1 = service.findEntityByDTO(dto);


        Assertions.assertNotNull(details1);

        Assertions.assertEquals(dto.getId(), details1.getId());
        Assertions.assertEquals(dto.getEmail(), details1.getBasicInformation().getEmail());
    }


}
