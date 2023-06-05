package ph.cdo.backend.service.data_jpa_test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import ph.cdo.backend.entity.user.Agent;
import ph.cdo.backend.enums.Role;
import ph.cdo.backend.repository.AgentRepository;

@DataJpaTest
@ActiveProfiles("test")
public class AgentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    @Qualifier("AgentRepository")
    private AgentRepository agentRepository;


    @BeforeEach
    void setUp(){
        agentRepository.deleteAll();
    }
    @Test
    public void testingIfEntityManagerNotNull(){
        Assertions.assertNotNull(entityManager);
    }

    @Test
    public void testingIfAgentRepositoryNotNull(){
        Assertions.assertNotNull(agentRepository);
    }

    @Test
    public void testingIfCanSaveInRepo(){
      Agent agent =   Agent.builder()
                .role(Role.Agent)
                .agentCode("1234")
                .email("sampleEmail@test.com")
                .password("password")
                .mobilePhone("123456")
                .build();

      agent = agentRepository.save(agent);

        // Flush the changes to the database
        entityManager.flush();
        entityManager.clear();

        Assertions.assertNotNull(agent);
        Assertions.assertTrue(agentRepository.findById(agent.getId()).isPresent());
        Assertions.assertEquals(agent.getAgentCode(), "1234");
        Assertions.assertEquals(agent.getEmail(), "sampleEmail@test.com");
        Assertions.assertEquals(agent.getPassword(), "password");
        Assertions.assertEquals(agent.getMobilePhone(), "123456");
;
    }
}
