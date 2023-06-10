package ph.cdo.backend.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class ErrorEntity<T> extends ResponseEntity<T> {
    public ErrorEntity(HttpStatusCode status) {
        super(status);
    }
}
