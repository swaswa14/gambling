package ph.cdo.backend.service.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ph.cdo.backend.config.SecurityConfig;
import ph.cdo.backend.controller.test.ExceptionThrowingController;

import ph.cdo.backend.exceptions.*;
import ph.cdo.backend.service.ApiErrorService;
import ph.cdo.backend.service.impl.JwtService;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(value = ExceptionThrowingController.class,   excludeAutoConfiguration = SecurityConfig.class)

public class CustomExceptionTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ApiErrorService apiErrorService;


    @MockBean
    private JwtService jwtService;

    @InjectMocks
    private ExceptionThrowingController exceptionThrowingController;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(exceptionThrowingController)     // instantiate controller.
                .setControllerAdvice(new GlobalExceptionHandler(apiErrorService))   // bind with controller advice.
                .build();
    }




    @Test
    public void testHandleEntityDoesNotExistsException() throws Exception {
        Long id = 1L;
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("http://localhost:8080/tests/exception/entity-does-not-exist/1")
                                .contentType(MediaType.ALL);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof EntityDoesNotExistsException))
                .andExpect(result -> Assertions.assertEquals( id + " does not exists",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    public void testHandleInvalidValueException() throws Exception {
        String value = "invalid";
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/tests/exception/invalid-value/" + value) // Pass value as PathVariable
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof InvalidValueException))
                .andExpect(result -> Assertions.assertEquals("Invalid Value: [" + value +"]",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    public void handleDuplicateEmailException() throws Exception {
        String value = "swaswa@gmail.com";
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/tests/exception/duplicate-email/" + value) // Pass value as PathVariable
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof DuplicateEmailException))
                .andExpect(result -> Assertions.assertEquals(String.format("email with %s already exists!", value.toLowerCase()),
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }


    @Test
    public void handleUserRegistrationError() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/tests/exception/registration-error/") // Pass value as PathVariable
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof UserRegistrationErrorException))
                .andExpect(result -> Assertions.assertEquals("There was an error encountered during the registration process",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    public void testHandleNullEntityException() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/tests/exception/null-entity"))

                .andExpect(status().isBadRequest())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof NullEntityException))
                .andExpect(result -> Assertions.assertEquals("Cannot save a Null from Entity",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }
}
