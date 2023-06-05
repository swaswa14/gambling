package ph.cdo.backend.service.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ResponseBody;
import ph.cdo.backend.config.SecurityConfig;
import ph.cdo.backend.controller.test.ExceptionThrowingController;

import ph.cdo.backend.errors.CustomExceptionHandler;
import ph.cdo.backend.errors.EntityDoesNotExistsException;
import ph.cdo.backend.errors.InvalidValueException;
import ph.cdo.backend.errors.NullEntityException;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(value = ExceptionThrowingController.class,   excludeAutoConfiguration = SecurityConfig.class)

public class CustomExceptionTest {
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private ExceptionThrowingController exceptionThrowingController;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(exceptionThrowingController)     // instantiate controller.
                .setControllerAdvice(new CustomExceptionHandler())   // bind with controller advice.
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
    public void testHandleNullEntityException() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/tests/exception/null-entity"))

                .andExpect(status().isBadRequest())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof NullEntityException))
                .andExpect(result -> Assertions.assertEquals("Cannot save a Null from Entity",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }
}
