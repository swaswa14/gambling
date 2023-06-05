package ph.cdo.backend.controller.test;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;
import ph.cdo.backend.errors.EntityDoesNotExistsException;
import ph.cdo.backend.errors.InvalidValueException;
import ph.cdo.backend.errors.NullEntityException;

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
}