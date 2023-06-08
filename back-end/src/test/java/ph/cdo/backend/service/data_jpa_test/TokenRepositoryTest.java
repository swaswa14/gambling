package ph.cdo.backend.service.data_jpa_test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import ph.cdo.backend.dto.records.TokenDTO;
import ph.cdo.backend.entity.Token;
import ph.cdo.backend.entity.user.Client;
import ph.cdo.backend.enums.Role;
import ph.cdo.backend.enums.TokenType;
import ph.cdo.backend.repository.ClientRepository;
import ph.cdo.backend.repository.TokenRepository;

import java.math.BigDecimal;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class TokenRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void testSaveClient() {
        // Create a new client
        Client client = Client.builder()
                .email("test@example.com")
                .password("password")
                .mobilePhone("123456789")
                .role(Role.Client)
                .balance(BigDecimal.valueOf(100.0))
                .build();

        // Save the client using the repository
        Client savedClient = clientRepository.save(client);

        // Flush the changes to the database
        entityManager.flush();
        entityManager.clear();

        // Retrieve the client from the database
        Client retrievedClient = clientRepository.findById(savedClient.getId()).orElse(null);

        // Perform assertions to check if the client is saved and retrieved correctly
        assertThat(retrievedClient).isNotNull();
        assertThat(retrievedClient.getEmail()).isEqualTo("test@example.com");
        assertThat(retrievedClient.getPassword()).isEqualTo("password");
        assertThat(retrievedClient.getMobilePhone()).isEqualTo("123456789");
        assertThat(retrievedClient.getRole()).isEqualTo(Role.Client);
        assertThat(retrievedClient.getBalance().doubleValue()).isEqualTo(100.0);




        Token token = Token.builder()
                .tokenType(TokenType.Enabled_Account)
                .user(retrievedClient)
                .expiryDate(new Date())
                .build();

        Token savedToken = tokenRepository.save(token);

        assertThat(token.getToken().equalsIgnoreCase(savedToken.getToken()));
        assertThat(token.getUser().getEmail().equalsIgnoreCase(savedToken.getUser().getEmail()));
        assertThat(savedToken.getId() != null);
    }






}