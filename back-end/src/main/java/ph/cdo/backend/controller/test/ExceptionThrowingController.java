package ph.cdo.backend.controller.test;

import org.springframework.web.bind.annotation.*;
import ph.cdo.backend.exceptions.*;

@RestController
@RequestMapping("/tests/exception")

public class ExceptionThrowingController {

    @GetMapping(value = "/entity-does-not-exist/{id}")
    public @ResponseBody String entityDoesNotExist(@PathVariable Long id) {
        throw new EntityDoesNotExistsException(id);
    }

    @GetMapping(value = "/invalid-value/{value}")
    public @ResponseBody String invalidValue(@PathVariable Object value) {
        throw new InvalidValueException(value);
    }

    @GetMapping(value = "/null-entity")
    public @ResponseBody String nullEntity() {
        throw new NullEntityException();
    }


    @GetMapping(value= "/duplicate-email/{email}")
    public @ResponseBody String duplicateEmail(@PathVariable String email){throw new DuplicateEmailException(email);}


    @GetMapping(value= "/registration-error")
    public @ResponseBody String registrationError(){throw new UserRegistrationErrorException();}

}