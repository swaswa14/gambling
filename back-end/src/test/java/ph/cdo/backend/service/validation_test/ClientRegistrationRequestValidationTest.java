package ph.cdo.backend.service.validation_test;



import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ph.cdo.backend.errors.validtion_group.Group5;
import ph.cdo.backend.request.ClientRegistrationRequest;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ClientRegistrationRequestValidationTest {
    private Validator validator;

    private ClientRegistrationRequest clientRegistrationRequest;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        clientRegistrationRequest = ClientRegistrationRequest.builder()
                .email("validemail@gmail.com")
                .password("ValidPassWord!!123")
                .invitationCode("1234")
                .mobilePhone("123-456-7890")
                .build();
    }

    @Test
    public void testEmailNotNull() {
        ClientRegistrationRequest form = ClientRegistrationRequest.builder()
                .email(null)
                .password(clientRegistrationRequest.getPassword())
                .invitationCode(clientRegistrationRequest.getInvitationCode())
                .mobilePhone(clientRegistrationRequest.getMobilePhone())
                .build();

        Set<ConstraintViolation<ClientRegistrationRequest>> violations = validator.validate(form);
        System.out.println(violations.toString());
        assertEquals(1, violations.size());
        ConstraintViolation<ClientRegistrationRequest> violation = violations.iterator().next();
        assertEquals("Email is required", violation.getMessage());
        assertEquals("email", violation.getPropertyPath().toString());
    }

    @Test
    public void testEmailFormat() {
        ClientRegistrationRequest form = ClientRegistrationRequest.builder()
                .email("invalid-email-format")
                .password("ValidPassword1!")
                .invitationCode("ValidCode")
                .build();

        Set<ConstraintViolation<ClientRegistrationRequest>> violations = validator.validate(form);
        assertEquals(1, violations.size());
        ConstraintViolation<ClientRegistrationRequest> violation = violations.iterator().next();
        assertEquals("Must be a valid email", violation.getMessage());
        assertEquals("email", violation.getPropertyPath().toString());
    }


    @Test
    public void whenPasswordIsValid_thenNoConstraintViolations() {
        ClientRegistrationRequest form = ClientRegistrationRequest.builder()
                .email(clientRegistrationRequest.getEmail())
                .password(clientRegistrationRequest.getPassword())
                .mobilePhone(clientRegistrationRequest.getMobilePhone())
                .invitationCode(clientRegistrationRequest.getInvitationCode())
                .build();
        Set<ConstraintViolation<ClientRegistrationRequest>> violations = validator.validate(form);
        assertEquals(0, violations.size());
    }

    @Test
    public void whenPasswordIsBlank_thenOneConstraintViolation() {
        ClientRegistrationRequest form = ClientRegistrationRequest.builder()
                .email(clientRegistrationRequest.getEmail())
                .password("")
                .mobilePhone(clientRegistrationRequest.getMobilePhone())
                .invitationCode(clientRegistrationRequest.getInvitationCode())
                .build();
        Set<ConstraintViolation<ClientRegistrationRequest>> violations = validator.validate(form);
        assertEquals(1, violations.size());
        assertEquals("Entered password is blank", violations.iterator().next().getMessage());
    }

    @Test
    public void whenPasswordIsEmpty_thenOneConstraintViolation() {
        ClientRegistrationRequest form = ClientRegistrationRequest.builder()
                .email(clientRegistrationRequest.getEmail())
                .password(" ")
                .mobilePhone(clientRegistrationRequest.getMobilePhone())
                .invitationCode(clientRegistrationRequest.getInvitationCode())
                .build();
        Set<ConstraintViolation<ClientRegistrationRequest>> violations = validator.validate(form);
        violations.forEach(t-> System.out.println(t.getMessage()));
        assertEquals(1, violations.size());
        assertEquals("Entered password is blank", violations.iterator().next().getMessage());
    }

    @Test
    public void whenPasswordIsNull_thenOneConstraintViolation() {
        ClientRegistrationRequest form = ClientRegistrationRequest.builder()
                .email(clientRegistrationRequest.getEmail())
                .password(null)
                .mobilePhone(clientRegistrationRequest.getMobilePhone())
                .invitationCode(clientRegistrationRequest.getInvitationCode())
                .build();
        Set<ConstraintViolation<ClientRegistrationRequest>> violations = validator.validate(form);
        assertEquals(1, violations.size());
        assertEquals("Password is required", violations.iterator().next().getMessage());
    }

    @Test
    public void whenPasswordLengthIsLessThan6_thenOneConstraintViolation() {
        ClientRegistrationRequest form = ClientRegistrationRequest.builder()
                .email(clientRegistrationRequest.getEmail())
                .password("Ab!2")
                .mobilePhone(clientRegistrationRequest.getMobilePhone())
                .invitationCode(clientRegistrationRequest.getInvitationCode())
                .build();
        Set<ConstraintViolation<ClientRegistrationRequest>> violations = validator.validate(form);
        assertEquals(1, violations.size());
        assertEquals("Password length should be between 6 and 255 characters", violations.iterator().next().getMessage());
    }

    @Test
    public void whenPasswordLengthIsMoreThan255_thenOneConstraintViolation() {
        String password = String.join("", Collections.nCopies(256, "A"));
        ClientRegistrationRequest form = ClientRegistrationRequest.builder()
                .email(clientRegistrationRequest.getEmail())
                .password(password)
                .mobilePhone(clientRegistrationRequest.getMobilePhone())
                .invitationCode(clientRegistrationRequest.getInvitationCode())
                .build();
        Set<ConstraintViolation<ClientRegistrationRequest>> violations = validator.validate(form);
        assertEquals(1, violations.size());
        assertEquals("Password length should be between 6 and 255 characters", violations.iterator().next().getMessage());
    }

    @Test
    public void whenPasswordDoesNotContainDigit_thenOneConstraintViolation() {
        ClientRegistrationRequest form = ClientRegistrationRequest.builder()
                .email(clientRegistrationRequest.getEmail())
                .password("Password!")
                .mobilePhone(clientRegistrationRequest.getMobilePhone())
                .invitationCode(clientRegistrationRequest.getInvitationCode())
                .build();
        Set<ConstraintViolation<ClientRegistrationRequest>> violations = validator.validate(form);
        assertEquals(1, violations.size());
        assertEquals("Password must contain at least 1 digit, 1 lowercase letter, 1 uppercase letter, and 1 special character", violations.iterator().next().getMessage());
    }

    @Test
    public void whenPasswordDoesNotContainLowercaseLetter_thenOneConstraintViolation() {
        ClientRegistrationRequest form = ClientRegistrationRequest.builder()
                .email(clientRegistrationRequest.getEmail())
                .password("PASSWORD1!")
                .mobilePhone(clientRegistrationRequest.getMobilePhone())
                .invitationCode(clientRegistrationRequest.getInvitationCode())
                .build();
        Set<ConstraintViolation<ClientRegistrationRequest>> violations = validator.validate(form);
        assertEquals(1, violations.size());
        assertEquals("Password must contain at least 1 digit, 1 lowercase letter, 1 uppercase letter, and 1 special character", violations.iterator().next().getMessage());
    }

    @Test
    public void whenPasswordDoesNotContainUppercaseLetter_thenOneConstraintViolation() {
        ClientRegistrationRequest form = ClientRegistrationRequest.builder()
                .email(clientRegistrationRequest.getEmail())
                .password("password1!")
                .mobilePhone(clientRegistrationRequest.getMobilePhone())
                .invitationCode(clientRegistrationRequest.getInvitationCode())
                .build();
        Set<ConstraintViolation<ClientRegistrationRequest>> violations = validator.validate(form);
        assertEquals(1, violations.size());
        assertEquals("Password must contain at least 1 digit, 1 lowercase letter, 1 uppercase letter, and 1 special character", violations.iterator().next().getMessage());
    }

    @Test
    public void whenPasswordDoesNotContainSpecialCharacter_thenOneConstraintViolation() {
        ClientRegistrationRequest form = ClientRegistrationRequest.builder()
                .email(clientRegistrationRequest.getEmail())
                .password("Password1")
                .mobilePhone(clientRegistrationRequest.getMobilePhone())
                .invitationCode(clientRegistrationRequest.getInvitationCode())
                .build();
        Set<ConstraintViolation<ClientRegistrationRequest>> violations = validator.validate(form);
        assertEquals(1, violations.size());
        assertEquals("Password must contain at least 1 digit, 1 lowercase letter, 1 uppercase letter, and 1 special character", violations.iterator().next().getMessage());
    }


    @Test
    public void whenEmailIsBlank_thenOneConstraintViolation() {
        ClientRegistrationRequest form = ClientRegistrationRequest.builder()
                .email(" ")
                .password(clientRegistrationRequest.getPassword())
                .invitationCode(clientRegistrationRequest.getInvitationCode())
                .mobilePhone(clientRegistrationRequest.getMobilePhone())
                .build();

        Set<ConstraintViolation<ClientRegistrationRequest>> violations = validator.validate(form);
        assertEquals(1, violations.size());
        ConstraintViolation<ClientRegistrationRequest> violation = violations.iterator().next();
        assertEquals("Entered email is blank", violation.getMessage());
        assertEquals("email", violation.getPropertyPath().toString());
    }

    @Test
    public void whenEmailIsEmpty_thenOneConstraintViolation() {
        ClientRegistrationRequest form = ClientRegistrationRequest.builder()
                .email("")
                .password(clientRegistrationRequest.getPassword())
                .invitationCode(clientRegistrationRequest.getInvitationCode())
                .mobilePhone(clientRegistrationRequest.getMobilePhone())
                .build();

        Set<ConstraintViolation<ClientRegistrationRequest>> violations = validator.validate(form);
        assertEquals(1, violations.size());
        ConstraintViolation<ClientRegistrationRequest> violation = violations.iterator().next();
        assertEquals("Entered email is blank", violation.getMessage());
        assertEquals("email", violation.getPropertyPath().toString());
    }

    @Test
    public void whenEmailIsNullAndBlank_thenOneConstraintViolation() {
        ClientRegistrationRequest form = ClientRegistrationRequest.builder()
                .email(null)
                .password(clientRegistrationRequest.getPassword())
                .invitationCode(clientRegistrationRequest.getInvitationCode())
                .mobilePhone(clientRegistrationRequest.getMobilePhone())
                .build();

        Set<ConstraintViolation<ClientRegistrationRequest>> violations = validator.validate(form);
        assertEquals(1, violations.size());
        ConstraintViolation<ClientRegistrationRequest> violation = violations.iterator().next();
        assertEquals("Email is required", violation.getMessage());
        assertEquals("email", violation.getPropertyPath().toString());
    }

    @Test
    public void whenEmailIsNullAndInvalid_thenOneConstraintViolation() {
        ClientRegistrationRequest form = ClientRegistrationRequest.builder()
                .email(null)
                .password(clientRegistrationRequest.getPassword())
                .invitationCode(clientRegistrationRequest.getInvitationCode())
                .mobilePhone(clientRegistrationRequest.getMobilePhone())
                .build();

        Set<ConstraintViolation<ClientRegistrationRequest>> violations = validator.validate(form);
        assertEquals(1, violations.size());
        ConstraintViolation<ClientRegistrationRequest> violation = violations.iterator().next();
        assertEquals("Email is required", violation.getMessage());
        assertEquals("email", violation.getPropertyPath().toString());
    }


    @Test
    public void whenInvitationCodeIsNotNumber_thenOneConstraintViolation() {
        ClientRegistrationRequest form = ClientRegistrationRequest.builder()
                .email(clientRegistrationRequest.getEmail())
                .password(clientRegistrationRequest.getPassword())
                .invitationCode("ABCD")
                .mobilePhone(clientRegistrationRequest.getMobilePhone())
                .build();

        Set<ConstraintViolation<ClientRegistrationRequest>> violations = validator.validate(form);
        assertEquals(1, violations.size());
        ConstraintViolation<ClientRegistrationRequest> violation = violations.iterator().next();
        assertEquals("Invitation code should be between 4 and 6 characters and must only contain a number", violation.getMessage());
        assertEquals("invitationCode", violation.getPropertyPath().toString());
    }

    @Test
    public void whenInvitationCodeIsLessThan4Characters_thenOneConstraintViolation() {
        ClientRegistrationRequest form = ClientRegistrationRequest.builder()
                .email(clientRegistrationRequest.getEmail())
                .password(clientRegistrationRequest.getPassword())
                .invitationCode("123")
                .mobilePhone(clientRegistrationRequest.getMobilePhone())
                .build();

        Set<ConstraintViolation<ClientRegistrationRequest>> violations = validator.validate(form);
        assertEquals(1, violations.size());
        ConstraintViolation<ClientRegistrationRequest> violation = violations.iterator().next();
        assertEquals("Invitation code should be between 4 and 6 characters and must only contain a number", violation.getMessage());
        assertEquals("invitationCode", violation.getPropertyPath().toString());
    }

    @Test
    public void whenInvitationCodeIsMoreThan6Characters_thenOneConstraintViolation() {
        ClientRegistrationRequest form = ClientRegistrationRequest.builder()
                .email(clientRegistrationRequest.getEmail())
                .password(clientRegistrationRequest.getPassword())
                .invitationCode("1234567")
                .mobilePhone(clientRegistrationRequest.getMobilePhone())
                .build();

        Set<ConstraintViolation<ClientRegistrationRequest>> violations = validator.validate(form);
        assertEquals(1, violations.size());
        ConstraintViolation<ClientRegistrationRequest> violation = violations.iterator().next();
        assertEquals("Invitation code should be between 4 and 6 characters and must only contain a number", violation.getMessage());
        assertEquals("invitationCode", violation.getPropertyPath().toString());
    }


    @Test
    public void whenMobilePhoneIsNotValidNumber_thenOneConstraintViolation() {
        ClientRegistrationRequest form = ClientRegistrationRequest.builder()
                .email(clientRegistrationRequest.getEmail())
                .password(clientRegistrationRequest.getPassword())
                .invitationCode(clientRegistrationRequest.getInvitationCode())
                .mobilePhone("123456")
                .build();

        Set<ConstraintViolation<ClientRegistrationRequest>> violations = validator.validate(form);
        assertEquals(1, violations.size());
        ConstraintViolation<ClientRegistrationRequest> violation = violations.iterator().next();
        assertEquals("Phone number is not a valid number", violation.getMessage());
        assertEquals("mobilePhone", violation.getPropertyPath().toString());
    }

    @Test
    public void whenInvitationCodeIsNull_thenNoConstraintViolation() {
        ClientRegistrationRequest form = ClientRegistrationRequest.builder()
                .email(clientRegistrationRequest.getEmail())
                .password(clientRegistrationRequest.getPassword())
                .invitationCode(null)
                .mobilePhone(clientRegistrationRequest.getMobilePhone())
                .build();

        Set<ConstraintViolation<ClientRegistrationRequest>> violations = validator.validate(form, Group5.class);
        assertEquals(0, violations.size());
    }

    @Test
    public void whenMobilePhoneIsNull_thenNoConstraintViolation() {
        ClientRegistrationRequest form = ClientRegistrationRequest.builder()
                .email(clientRegistrationRequest.getEmail())
                .password(clientRegistrationRequest.getPassword())
                .invitationCode(clientRegistrationRequest.getInvitationCode())
                .mobilePhone(null)
                .build();

        Set<ConstraintViolation<ClientRegistrationRequest>> violations = validator.validate(form, Group5.class);
        assertEquals(0, violations.size());
    }



}
