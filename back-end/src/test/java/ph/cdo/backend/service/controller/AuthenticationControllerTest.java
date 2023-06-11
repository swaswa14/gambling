package ph.cdo.backend.service.controller;


import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import ph.cdo.backend.controller.auth.AuthenticationController;
import ph.cdo.backend.dto.records.ClientDTOEntity;
import ph.cdo.backend.entity.user.Client;
import ph.cdo.backend.enums.Role;
import ph.cdo.backend.request.AuthenticationRequest;
import ph.cdo.backend.request.registration.ClientBasicRegistrationForm;
import ph.cdo.backend.service.impl.authentication.AuthenticationService;
import ph.cdo.backend.service.impl.authentication.ClientAuthenticationService;
import ph.cdo.backend.service.impl.user.ClientService;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AuthenticationControllerTest {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private AuthenticationController controller;

    @Autowired
    @Qualifier("ClientAuthenticationService")
    private ClientAuthenticationService authenticationService;

    @Autowired private  ClientService clientService;


    private TestRestTemplate testRestTemplate;
    private ClientBasicRegistrationForm request;


    @Autowired private PasswordEncoder passwordEncoder;

    HttpHeaders headers;



    private Gson gson;

    @BeforeEach
    public void setUp() {
//        this.mockMvc = MockMvcBuilders
//                .standaloneSetup(controller)     // instantiate controller.
//                .setControllerAdvice(new GlobalExceptionHandler())   // bind with controller advice.
//                .build();

        //setting up request
        request = ClientBasicRegistrationForm
                .builder()
                .invitationCode("123456")
                .mobilePhone("123456789")
                .email("joshuagarrysalcedo@yopmail.com")
                .password("ValidPassword123!!")
                .firstName("Joshua")
                .lastName("Salcedo")
                .build();

        this.gson = new Gson();

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);





        testRestTemplate = new TestRestTemplate();
    }



    @Test
    public void testingIfNotNull(){
        Assertions.assertNotNull(gson);
        Assertions.assertNotNull(request);
        Assertions.assertNotNull(controller);
        Assertions.assertNotNull(gson.toJson(request));
    }

    @Test
    public void testingAValidClientRegistration() throws Exception
    {



        final String baseUrl = "http://localhost:"+ randomServerPort+ "/api/v1/auth/register/client";

        URI uri = new URI(baseUrl);


        HttpEntity<ClientBasicRegistrationForm> myRequest = new HttpEntity<>(request, headers);

        ResponseEntity<Object> response = this.testRestTemplate.postForEntity(uri, myRequest, Object.class);


        System.out.println(response);
        Assertions.assertEquals(201,response.getStatusCode().value());


    }


    @Test
    public void invalidEmailClientRegistrationTest() throws Exception {
        final String baseUrl = "http://localhost:" + randomServerPort + "/api/v1/auth/register/client";

        URI uri = new URI(baseUrl);

        // Making email invalid
        request.setEmail("invalidEmail");

        HttpEntity<ClientBasicRegistrationForm> myRequest = new HttpEntity<>(request, headers);
        ResponseEntity<Object> response = this.testRestTemplate.postForEntity(uri, myRequest, Object.class);

        System.out.println(response);
        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.getStatusCode().value());
    }


    @Test
    public void invalidPasswordClientRegistrationTest() throws Exception {
        final String baseUrl = "http://localhost:" + randomServerPort + "/api/v1/auth/register/client";

        URI uri = new URI(baseUrl);

        // Making password invalid
        request.setPassword("1234");

        HttpEntity<ClientBasicRegistrationForm> myRequest = new HttpEntity<>(request, headers);
        ResponseEntity<Object> response = this.testRestTemplate.postForEntity(uri, myRequest, Object.class);

        System.out.println(response);
        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.getStatusCode().value());
    }


    @Test
    public void multipleInvalidFieldsClientRegistrationTest() throws Exception {
        final String baseUrl = "http://localhost:" + randomServerPort + "/api/v1/auth/register/client";

        URI uri = new URI(baseUrl);

        // Making both email and password invalid
        request.setEmail("invalidEmail");
        request.setPassword("1234");

        HttpEntity<ClientBasicRegistrationForm> myRequest = new HttpEntity<>(request, headers);
        ResponseEntity<Object> response = this.testRestTemplate.postForEntity(uri, myRequest, Object.class);

        System.out.println(response);
        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.getStatusCode().value());

        // Assuming your response body is a map of field to error message
        var responseBody=  response.getBody();
        System.out.println(responseBody.toString());
        Assertions.assertNotNull(responseBody);



    }


    @Test
    public void testingDuplicateEmail() throws URISyntaxException {
        final String baseUrl = "http://localhost:"+ randomServerPort+ "/api/v1/auth/register/client";

        URI uri = new URI(baseUrl);
        request.setEmail("swaswaEmail@gmail.com");

        HttpEntity<ClientBasicRegistrationForm> myRequest = new HttpEntity<>(request, headers);

        ResponseEntity<Object> response = this.testRestTemplate.postForEntity(uri, myRequest, Object.class);


        System.out.println(response);
        Assertions.assertEquals(201,response.getStatusCode().value());


        response = this.testRestTemplate.postForEntity(uri, myRequest, Object.class);

        System.out.println(response);

        Assertions.assertEquals(HttpStatus.CONFLICT.value(), response.getStatusCode().value());
    }



    @Test
    public void testingAValidAuthentication() throws Exception {
        Client client = Client.builder()
                .role(Role.Client)
                .email("swaswa14@gmail.com")
                .password(passwordEncoder.encode(request.getPassword()))
                .mobilePhone(request.getMobilePhone())
                .balance(BigDecimal.valueOf(1000_000L))
                .isEnabled(true)
                .isLocked(false)
                .build();

        System.out.println("Pass " + client.getPassword());

        ClientDTOEntity clientDTO = clientService.save(client);

        Assertions.assertNotNull(clientDTO);

        System.out.println(clientDTO.email());

        // Setup the AuthenticationRequest object
        AuthenticationRequest authRequest = AuthenticationRequest.builder()
                .username("swaswa14@gmail.com")
                .password("ValidPassword123!!")
                .build();



        // Setup the URL
        final String baseUrl = "http://localhost:" + randomServerPort + "/api/v1/auth/authenticate";
        URI uri = new URI(baseUrl);

        // Make the request
        HttpEntity<AuthenticationRequest> myRequest = new HttpEntity<>(authRequest, headers);
        ResponseEntity<Object> response = this.testRestTemplate.postForEntity(uri, myRequest,Object.class);
        System.out.println(response.getBody());
        // Assert that the response status is OK (200)
        Assertions.assertEquals(200, response.getStatusCode().value());



//        // Assert that the Set-Cookie header exists and contains a token
//        List<String> setCookieHeaders = response.getHeaders().get(HttpHeaders.SET_COOKIE);
//        Assertions.assertNotNull(setCookieHeaders);
//        Assertions.assertTrue(setCookieHeaders.stream().anyMatch(header -> header.startsWith("token=")));


        Assertions.assertNotNull( response.getBody());
    }





}

