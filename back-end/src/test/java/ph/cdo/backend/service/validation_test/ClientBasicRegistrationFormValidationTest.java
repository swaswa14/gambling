package ph.cdo.backend.service.validation_test;



import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.experimental.SuperBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ph.cdo.backend.request.registration.BasicRegistrationForm;
import ph.cdo.backend.request.registration.ClientBasicRegistrationForm;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ClientBasicRegistrationFormValidationTest {
    private Validator validator;

    private ClientBasicRegistrationForm request;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        request = ClientBasicRegistrationForm.builder()
                .email("validemail@gmail.com")
                .password("ValidPassWord!!123")

                .invitationCode("1234")
                .mobilePhone("123-456-7890")
                .firstName("swaswa")
                .lastName("salcedo")
                .build();
    }

    @Test
    public void testEmailNotNull() {
       request.setEmail(null);

        Set<ConstraintViolation<ClientBasicRegistrationForm>> violations = validator.validate(request);
        System.out.println(violations.toString());
        assertEquals(2, violations.size());
        ConstraintViolation<ClientBasicRegistrationForm> violation = violations.iterator().next();

        assertEquals("email", violation.getPropertyPath().toString());
    }

    @Test
    public void testEmailFormat() {

             request.setEmail("invalid-email-format");

        Set<ConstraintViolation<ClientBasicRegistrationForm>> violations = validator.validate(request);
        assertEquals(1, violations.size());
        ConstraintViolation<ClientBasicRegistrationForm> violation = violations.iterator().next();

        assertEquals("email", violation.getPropertyPath().toString());
    }


    @Test
    public void whenPasswordIsValid_thenNoConstraintViolations() {

        Set<ConstraintViolation<ClientBasicRegistrationForm>> violations = validator.validate(request);
        assertEquals(0, violations.size());
    }

    @Test
    public void whenPasswordIsBlank_thenOneConstraintViolation() {
        request.setPassword(null);
        Set<ConstraintViolation<ClientBasicRegistrationForm>> violations = validator.validate(request);
        assertEquals(1, violations.size());
  ;
    }

    @Test
    public void whenPasswordIsEmpty_thenOneConstraintViolation() {
        request.setPassword(" ");
        Set<ConstraintViolation<ClientBasicRegistrationForm>> violations = validator.validate(request);
        violations.forEach(t-> System.out.println(t.getMessage()));
        assertEquals(3, violations.size());

    }

    @Test
    public void whenPasswordIsNull_thenOneConstraintViolation() {
        request.setPassword(null);
        Set<ConstraintViolation<ClientBasicRegistrationForm>> violations = validator.validate(request);
        assertEquals(1, violations.size());
        assertEquals("Password is required", violations.iterator().next().getMessage());
    }

    @Test
    public void whenPasswordLengthIsLessThan6_thenOneConstraintViolation() {
       request.setPassword("Ab!2");
        Set<ConstraintViolation<ClientBasicRegistrationForm>> violations = validator.validate(request);
        assertEquals(2, violations.size());

    }

    @Test
    public void whenPasswordLengthIsMoreThan255_thenOneConstraintViolation() {
        String password = String.join("", Collections.nCopies(256, "A"));
        request.setPassword(password);
        Set<ConstraintViolation<ClientBasicRegistrationForm>> violations = validator.validate(request);
        assertEquals(2, violations.size());

    }

    @Test
    public void whenPasswordDoesNotContainDigit_thenOneConstraintViolation() {
        request.setPassword("password!!");
        Set<ConstraintViolation<ClientBasicRegistrationForm>> violations = validator.validate(request);
        assertEquals(1, violations.size());
        assertEquals("Password must contain at least 1 digit, 1 lowercase letter, 1 uppercase letter, and 1 special character", violations.iterator().next().getMessage());
    }

    @Test
    public void whenPasswordDoesNotContainLowercaseLetter_thenOneConstraintViolation() {
        request.setPassword("password123...");
        Set<ConstraintViolation<ClientBasicRegistrationForm>> violations = validator.validate(request);
        assertEquals("Password must contain at least 1 digit, 1 lowercase letter, 1 uppercase letter, and 1 special character", violations.iterator().next().getMessage());

        assertEquals(1, violations.size());
    }

    @Test
    public void whenPasswordDoesNotContainUppercaseLetter_thenOneConstraintViolation() {
        request.setPassword("password1");
        Set<ConstraintViolation<ClientBasicRegistrationForm>> violations = validator.validate(request);
        assertEquals(1, violations.size());

    }

    @Test
    public void whenPasswordDoesNotContainSpecialCharacter_thenOneConstraintViolation() {
       request.setPassword("password1");
        Set<ConstraintViolation<ClientBasicRegistrationForm>> violations = validator.validate(request);
        assertEquals(1, violations.size());

    }


    @Test
    public void whenEmailIsBlank_thenOneConstraintViolation() {
        request.setEmail(" ");

        Set<ConstraintViolation<ClientBasicRegistrationForm>> violations = validator.validate(request);
        assertEquals(2, violations.size());
        ConstraintViolation<ClientBasicRegistrationForm> violation = violations.iterator().next();

        assertEquals("email", violation.getPropertyPath().toString());
    }

    @Test
    public void whenEmailIsEmpty_thenOneConstraintViolation() {
        request.setEmail("");

        Set<ConstraintViolation<ClientBasicRegistrationForm>> violations = validator.validate(request);
        assertEquals(1, violations.size());
        ConstraintViolation<ClientBasicRegistrationForm> violation = violations.iterator().next();

        assertEquals("email", violation.getPropertyPath().toString());
    }

    @Test
    public void whenEmailIsNullAndBlank_thenOneConstraintViolation() {
        request.setEmail(null);

        Set<ConstraintViolation<ClientBasicRegistrationForm>> violations = validator.validate(request);
        assertEquals(2, violations.size());
        ConstraintViolation<ClientBasicRegistrationForm> violation = violations.iterator().next();

        assertEquals("email", violation.getPropertyPath().toString());
    }

    @Test
    public void whenEmailIsNullAndInvalid_thenOneConstraintViolation() {
        request.setEmail(null);

        Set<ConstraintViolation<ClientBasicRegistrationForm>> violations = validator.validate(request);
        assertEquals(2, violations.size());
        ConstraintViolation<ClientBasicRegistrationForm> violation = violations.iterator().next();

        assertEquals("email", violation.getPropertyPath().toString());
    }


    @Test
    public void whenInvitationCodeIsNotNumber_thenOneConstraintViolation() {
       request.setInvitationCode("ABC");

        Set<ConstraintViolation<ClientBasicRegistrationForm>> violations = validator.validate(request);
        assertEquals(1, violations.size());
        ConstraintViolation<ClientBasicRegistrationForm> violation = violations.iterator().next();

        assertEquals("invitationCode", violation.getPropertyPath().toString());
    }

    @Test
    public void whenInvitationCodeIsLessThan4Characters_thenOneConstraintViolation() {
        request.setInvitationCode("111");

        Set<ConstraintViolation<ClientBasicRegistrationForm>> violations = validator.validate(request);
        assertEquals(1, violations.size());
        ConstraintViolation<ClientBasicRegistrationForm> violation = violations.iterator().next();
        assertEquals("invitationCode", violation.getPropertyPath().toString());
    }

    @Test
    public void whenInvitationCodeIsMoreThan6Characters_thenOneConstraintViolation() {
       request.setInvitationCode("1234567");

        Set<ConstraintViolation<ClientBasicRegistrationForm>> violations = validator.validate(request);
        assertEquals(1, violations.size());
        ConstraintViolation<ClientBasicRegistrationForm> violation = violations.iterator().next();

        assertEquals("invitationCode", violation.getPropertyPath().toString());
    }


    @Test
    public void whenMobilePhoneIsNotValidNumber_thenOneConstraintViolation() {
        request.setMobilePhone("123456");
        Set<ConstraintViolation<ClientBasicRegistrationForm>> violations = validator.validate(request);
        assertEquals(1, violations.size());
        ConstraintViolation<ClientBasicRegistrationForm> violation = violations.iterator().next();

        assertEquals("mobilePhone", violation.getPropertyPath().toString());
    }

    @Test
    public void whenInvitationCodeIsNull_thenNoConstraintViolation() {
       request.setInvitationCode(null);

        Set<ConstraintViolation<ClientBasicRegistrationForm>> violations = validator.validate(request);
        assertEquals(0, violations.size());
    }

    @Test
    public void whenMobilePhoneIsNull_thenNoConstraintViolation() {
        request.setMobilePhone(null);

        Set<ConstraintViolation<ClientBasicRegistrationForm>> violations = validator.validate(request);
        assertEquals(0, violations.size());
    }



}
