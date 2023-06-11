package ph.cdo.backend.service.data_jpa_test;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import ph.cdo.backend.entity.base.Name;
import ph.cdo.backend.entity.user.Admin;
import ph.cdo.backend.enums.Role;
import ph.cdo.backend.repository.AdminRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@DataJpaTest
@ActiveProfiles("test")
public class AdminRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    @Qualifier("AdminRepository")
    private AdminRepository adminRepository;



    @Test
    public void testSaveClient() {
        Name name = mock(Name.class);
        // Create a new client
        Admin admin = Admin.builder()
                .role(Role.Admin)
                .name(name )
                .email("swaswa@gmail.com")
                .password("password")
                .mobilePhone("123456789")
                .build();

        // Save the client using the repository
        Admin savedAdmin = adminRepository.save(admin);

        // Flush the changes to the database
        entityManager.flush();
        entityManager.clear();

        // Retrieve the client from the database
        Admin retrievedClient = adminRepository.findById(savedAdmin.getId()).orElse(null);

        // Perform assertions to check if the client is saved and retrieved correctly
        assertThat(retrievedClient).isNotNull();
        assertThat(retrievedClient.getId()).isEqualTo(1L);
        assertThat(retrievedClient.getEmail()).isEqualTo("swaswa@gmail.com");
        assertThat(retrievedClient.getPassword()).isEqualTo("password");
        assertThat(retrievedClient.getMobilePhone()).isEqualTo("123456789");
        assertThat(retrievedClient.getRole()).isEqualTo(Role.Admin);
    }
}
