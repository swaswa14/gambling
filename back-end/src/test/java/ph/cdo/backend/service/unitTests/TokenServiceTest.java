package ph.cdo.backend.service.unitTests;


import com.github.javafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Qualifier;
import ph.cdo.backend.dto.mapper.impl.user.ClientDTOMapper;
import ph.cdo.backend.dto.mapper.impl.user.TokenDTOMapper;
import ph.cdo.backend.dto.records.ClientDTOEntity;
import ph.cdo.backend.dto.records.TokenDTO;
import ph.cdo.backend.entity.Token;
import ph.cdo.backend.entity.user.Client;
import ph.cdo.backend.enums.Role;
import ph.cdo.backend.enums.TokenType;
import ph.cdo.backend.repository.ClientRepository;
import ph.cdo.backend.repository.TokenRepository;
import ph.cdo.backend.service.impl.user.ClientServiceImpl;
import ph.cdo.backend.service.impl.TokenServiceImpl;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TokenServiceTest {



    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private static TokenDTOMapper tokenDTOMapper;



    @Mock
    private ClientDTOMapper clientDTOMapper;

    @InjectMocks
    private TokenServiceImpl tokenService;

    @InjectMocks
    private ClientServiceImpl clientService;

    private final static Faker faker = new Faker();

    @Mock
    @Qualifier("ClientRepository")
    private ClientRepository clientRepository;



    private ClientDTOEntity clientDTO;


    private Client client;



    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        this.client = createRandomClient();
//
//        when(clientService.save(createRandomClient())).thenReturn(clientDTO);
    }


    @Test
    public void testingIfNotNull(){
        Assertions.assertNotNull(tokenService);
        Assertions.assertNotNull(clientService);
        Assertions.assertNotNull(client);
    }


    @Test
    public void testingIfTokenNotNull(){
        Token token = createRandomToken();
        TokenDTO tokenDTO = new TokenDTO(
                token.getId(),
                token.getUser().getId(),
                token.getUser().getEmail(),
                token.getToken(),
                token.getTokenType(),
                token.getExpiryDate()

        );

        when(tokenService.create(token)).thenReturn(tokenDTO);

        tokenDTO = tokenService.create(token);


        System.out.println("Token\n " + tokenDTO);

        Assertions.assertNotNull(tokenDTO);
    }

    @Test
    public void testingGettingAllToken(){
        tokenService.findAll();
        verify(tokenRepository).findAll();
    }


    @Test
    public void testFindById() {
        Long id = 1L;  // Or another appropriate value
        Token token = createRandomToken();
        TokenDTO mockTokenDTO = new TokenDTO(
                id,
                token.getUser().getId(),
                token.getUser().getEmail(),
                token.getToken(),
                token.getTokenType(),
                token.getExpiryDate()

        );
        // Set properties of mockTokenDTO as necessary

        when(tokenRepository.findById(id)).thenReturn(Optional.of(token));

        when(tokenService.findById(id)).thenReturn(mockTokenDTO);

        TokenDTO tokenDTO = tokenService.findById(id);

        System.out.println(tokenDTO);

        Assertions.assertNotNull(tokenDTO);

        Assertions.assertEquals(id, tokenDTO.getID());
        // You can add more assertions to check the properties of the tokenDTO
    }
    @Test
    public void testUpdateEntity() {
        Long id = 1L;
        Token originalToken = createRandomToken();
        Token updatedToken = createRandomToken(); // You may want to use a different method to generate an updated token

        System.out.println("OToken " + originalToken );
        System.out.println("UToken " + updatedToken);

        Assertions.assertNotEquals(originalToken, updatedToken);

        TokenDTO originalTokenDTO = new TokenDTO(
                id,
                originalToken.getUser().getId(),
                originalToken.getUser().getEmail(),
                originalToken.getToken(),
                originalToken.getTokenType(),
                originalToken.getExpiryDate()
        );

        TokenDTO updatedTokenDTO = new TokenDTO(
                id,
                updatedToken.getUser().getId(),
                updatedToken.getUser().getEmail(),
                updatedToken.getToken(),
                TokenType.Change_Password,
                updatedToken.getExpiryDate()
        );

        // Simulate the changing behavior of tokenRepository.findById(id)
        Answer<Optional<Token>> answer = new Answer<Optional<Token>>() {
            private int count = 0;

            @Override
            public Optional<Token> answer(InvocationOnMock invocation) {
                count++;
                if (count == 1) {
                    return Optional.of(originalToken);
                } else {
                    return Optional.of(updatedToken);
                }
            }
        };

        when(tokenRepository.findById(id)).thenAnswer(answer);
        when(tokenRepository.save(originalToken)).thenReturn(originalToken);

        when(tokenService.create(originalToken)).thenReturn(originalTokenDTO);
        when(tokenService.updateEntity(id, updatedToken)).thenReturn(updatedTokenDTO);


        TokenDTO originalCreatedToken = tokenService.create(originalToken);

        System.out.println("Original Token ");
        System.out.println(originalCreatedToken);
        when(tokenDTOMapper.apply(updatedToken)).thenReturn(updatedTokenDTO);

        TokenDTO updatedResultTokenDTO = tokenService.updateEntity(id, updatedToken);

        System.out.println("Updated Token ");
        System.out.println(updatedResultTokenDTO);

        Assertions.assertNotNull(originalCreatedToken);
        Assertions.assertNotEquals(originalCreatedToken, updatedResultTokenDTO);
        // Here you can add more assertions to check the properties of the returned DTO.
        // If you know how the update method changes the token, you can check whether these changes are reflected in the returned DTO
        // For example, if the update method changes the token type, you could use:
        // Assertions.assertEquals(updatedToken.getTokenType(), originalCreatedToken.getTokenType());
    }

    @Test
    public void testDeleteToken() {
        // Arrange
        // Arrange
        Token newToken = createRandomToken();
        newToken.setId(1L);
        TokenDTO expectedTokenDTO = new TokenDTO(
                1L,
                newToken.getUser().getId(),
                newToken.getUser().getEmail(),
                newToken.getToken(),
                newToken.getTokenType(),
                newToken.getExpiryDate()
        );

        Answer<Optional<Token>> answer = new Answer<Optional<Token>>() {
            private int count = 0;

            @Override
            public Optional<Token> answer(InvocationOnMock invocation) {
                count++;
                if (count == 1) {
                    return Optional.of(newToken);
                } else {
                    return Optional.empty();
                }
            }
        };
        when(tokenService.create(newToken)).thenReturn(expectedTokenDTO);
        when(tokenRepository.findById(1L)).then(answer);

        // Assume that the token exists in the repository.


        // Act
       boolean result =  tokenService.deleteEntity(1L);

        // Assert
        // Verify that the deleteById method was called on the repository with the correct ID.
        verify(tokenRepository, times(1)).deleteById(1L);

        Assertions.assertTrue(result);
    }
    @Test
    public void testCreate() {
        // Arrange
        Token newToken = createRandomToken();
        newToken.setId(1L);
        TokenDTO expectedTokenDTO = new TokenDTO(
                1L,
                newToken.getUser().getId(),
                newToken.getUser().getEmail(),
                newToken.getToken(),
                newToken.getTokenType(),
                newToken.getExpiryDate()
        );

        System.out.println("Token " + newToken);
        System.out.println("Expected token " + expectedTokenDTO);


        // Here, we're assuming that your TokenService's create method uses TokenRepository's save method
        when(tokenRepository.save(newToken)).thenReturn(newToken);
        when(tokenDTOMapper.apply(newToken)).thenReturn(expectedTokenDTO);
        when(tokenService.create(newToken)).thenReturn(expectedTokenDTO);


        // Act
        TokenDTO actualTokenDTO = tokenService.create(newToken);



        System.out.println("Token " + newToken);
        System.out.println("Expected token " + expectedTokenDTO);

        // Assert
        Assertions.assertNotNull(actualTokenDTO);
        Assertions.assertEquals(expectedTokenDTO, actualTokenDTO);

        // Also assert the token's fields to make sure they match
        Assertions.assertEquals(1L, actualTokenDTO.id());
        Assertions.assertEquals(newToken.getUser().getEmail(), actualTokenDTO.userEmail());
        Assertions.assertEquals(newToken.getToken(), actualTokenDTO.token());
        Assertions.assertEquals(newToken.getTokenType(), actualTokenDTO.tokenType());
        Assertions.assertEquals(newToken.getExpiryDate(), actualTokenDTO.expiryDate());
    }


    public static Client createRandomClient() {
        return Client.builder()
                .role(Role.Client)
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .mobilePhone(faker.phoneNumber().cellPhone())
                .balance(BigDecimal.valueOf(faker.number().randomDouble(2, 0, 10000)))
                .id(faker.number().numberBetween(1L, 1000L))
                .build();
    }

    private static Token createRandomToken(){
        return Token.builder()
                .tokenType(TokenType.Enabled_Account)
                .id(faker.number().numberBetween(1L, 1000L))
                .user(createRandomClient())
                .expiryDate(faker.date().future(10, TimeUnit.DAYS))
                .build();
    }

}
