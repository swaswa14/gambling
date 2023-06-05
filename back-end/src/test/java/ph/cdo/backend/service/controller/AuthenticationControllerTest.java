package ph.cdo.backend.service.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import ph.cdo.backend.controller.auth.AuthenticationController;
import ph.cdo.backend.errors.ApiError;
import ph.cdo.backend.errors.CustomExceptionHandler;
import ph.cdo.backend.request.ClientRegistrationRequest;
import ph.cdo.backend.response.ClientRegistrationResponse;
import ph.cdo.backend.response.IResponseBody;
import ph.cdo.backend.response.ResponseObject;
import ph.cdo.backend.service.AuthenticationService;
import ph.cdo.backend.service.JwtService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AuthenticationControllerTest {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private AuthenticationController controller;


    private TestRestTemplate testRestTemplate;
    private ClientRegistrationRequest request;

    HttpHeaders headers;



    private Gson gson;

    @BeforeEach
    public void setUp() {
//        this.mockMvc = MockMvcBuilders
//                .standaloneSetup(controller)     // instantiate controller.
//                .setControllerAdvice(new CustomExceptionHandler())   // bind with controller advice.
//                .build();

        //setting up request
        request = ClientRegistrationRequest
                .builder()
                .invitationCode("123456")
                .mobilePhone("123456789")
                .email("sample@gmail.com")
                .password("ValidPassword123!!")
                .build();

        this.gson = new Gson();

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));



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


        HttpEntity<ClientRegistrationRequest> myRequest = new HttpEntity<>(request, headers);

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

        HttpEntity<ClientRegistrationRequest> myRequest = new HttpEntity<>(request, headers);
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

        HttpEntity<ClientRegistrationRequest> myRequest = new HttpEntity<>(request, headers);
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

        HttpEntity<ClientRegistrationRequest> myRequest = new HttpEntity<>(request, headers);
        ResponseEntity<Object> response = this.testRestTemplate.postForEntity(uri, myRequest, Object.class);

        System.out.println(response);
        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.getStatusCode().value());

        // Assuming your response body is a map of field to error message
        var responseBody=  response.getBody();

        Assertions.assertNotNull(responseBody);
        System.out.println(responseBody.toString());


    }


    @Test
    public void testingDuplicateEmail() throws URISyntaxException {
        final String baseUrl = "http://localhost:"+ randomServerPort+ "/api/v1/auth/register/client";

        URI uri = new URI(baseUrl);
        request.setEmail("swaswaEmail@gmail.com");

        HttpEntity<ClientRegistrationRequest> myRequest = new HttpEntity<>(request, headers);

        ResponseEntity<Object> response = this.testRestTemplate.postForEntity(uri, myRequest, Object.class);


        System.out.println(response);
        Assertions.assertEquals(201,response.getStatusCode().value());


        response = this.testRestTemplate.postForEntity(uri, myRequest, Object.class);

        System.out.println(response);

        Assertions.assertEquals(HttpStatus.CONFLICT.value(), response.getStatusCode().value());
    }









}

