package ph.jsalcedo.backendv2.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import ph.jsalcedo.backendv2.entity.user.User;
import ph.jsalcedo.backendv2.entity.user.details.ClientDetails;

import static org.mockito.Mockito.mock;
@DataJpaTest
@ActiveProfiles("test")
public class ClientDetailsRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    @Qualifier("ClientRepository")
    private ClientDetailsRepository repository;


    @Test
    public void testIfNotNull(){
        Assertions.assertNotNull(entityManager);
        Assertions.assertNotNull(repository);
    }

    @Test
    public void testSaveUser(){
        ClientDetails details = mock(ClientDetails.class);

        Assertions.assertNotNull(details);

        ClientDetails savedUser = repository.save(details);

        Assertions.assertNotNull(savedUser);
        Assertions.assertNotNull(savedUser.getId());
    }
}
