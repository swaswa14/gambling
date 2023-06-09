package ph.cdo.backend.service.validation_test;
import jakarta.validation.ConstraintViolation;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ph.cdo.backend.request.ClientDepositRequest;

import java.math.BigDecimal;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ClientDepositTest {

    private Validator validator;


    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    @Test
    public void testValidBigDecimal() {
        ClientDepositRequest request = ClientDepositRequest.builder().amount(new BigDecimal("10")).build();
        Set<ConstraintViolation<ClientDepositRequest>> violations = validator.validate(request);
        assertEquals(0, violations.size());
    }

    @Test
    public void testMinimumValue() {
        ClientDepositRequest request = ClientDepositRequest.builder().amount(new BigDecimal("0")).build();
        Set<ConstraintViolation<ClientDepositRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());
        assertEquals("Minimum deposit amount is 1$", violations.iterator().next().getMessage());
    }

    @Test
    public void testBlankValue() {
        ClientDepositRequest request = ClientDepositRequest.builder().amount(null).build();
        Set<ConstraintViolation<ClientDepositRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());
        assertEquals("Must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    public void testNonPositiveIntegerValue() {
        ClientDepositRequest request = ClientDepositRequest.builder().amount(new BigDecimal("-1.5")).build();
        Set<ConstraintViolation<ClientDepositRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());
        assertEquals("Minimum deposit amount is 1$", violations.iterator().next().getMessage());
    }
}
