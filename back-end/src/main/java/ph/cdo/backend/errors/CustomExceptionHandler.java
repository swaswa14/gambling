package ph.cdo.backend.errors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(EntityDoesNotExistsException.class)
    public ResponseEntity<Object> handleEntityDoesNotExistsException(
            EntityDoesNotExistsException ex){
        ApiError error = apiErrorBuilder(ex, HttpStatus.NOT_FOUND);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(InvalidValueException.class)
    public ResponseEntity<Object> handleInvalidValueException(
            InvalidValueException ex, WebRequest request){
        ApiError error = apiErrorBuilder(ex, HttpStatus.UNPROCESSABLE_ENTITY);

        return handleExceptionInternal(ex, error, new HttpHeaders(), error.getStatus(), request);
    }

    @ExceptionHandler(NullEntityException.class)
    public ResponseEntity<Object> handleNullEntityException(NullEntityException ex, WebRequest request){
        ApiError error = apiErrorBuilder(ex, HttpStatus.BAD_REQUEST);

        return handleExceptionInternal(ex, error, new HttpHeaders(), error.getStatus(), request);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<Object> handleDuplicateEmailException(DuplicateEmailException ex, WebRequest request){
        ApiError error = apiErrorBuilder(ex, HttpStatus.CONFLICT);

        return handleExceptionInternal(ex, error, new HttpHeaders(), error.getStatus(), request);
    }


    @ExceptionHandler(UserRegistrationErrorException.class)
    public ResponseEntity<Object> handleUserRegistrationErrorException(UserRegistrationErrorException ex, WebRequest request){
        ApiError error = apiErrorBuilder(ex, HttpStatus.BAD_REQUEST);

        return handleExceptionInternal(ex, error, new HttpHeaders(), error.getStatus(), request);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<Object> handleInvalidRequestException(InvalidRequestException ex) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                   ex.getErrors()

            );
    }
    private ApiError apiErrorBuilder(Exception ex, HttpStatus httpStatus){
        return ApiError.builder()
                .errorMessage(ex.getMessage())
                .status(httpStatus)
                .statusCode(Integer.toString(httpStatus.value()))
                .timeStamp(ZonedDateTime.now(ZoneId.of("Z")))
                .exception(ex.getClass().getSimpleName())
                .build();
    }
}
