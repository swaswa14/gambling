package ph.cdo.backend.errors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ph.cdo.backend.dto.FieldErrorDTO;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(EntityDoesNotExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleEntityDoesNotExistsException(
            EntityDoesNotExistsException ex){
        ApiError error = apiErrorBuilder(ex, HttpStatus.NOT_FOUND);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(InvalidValueException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<Object> handleInvalidValueException(
            InvalidValueException ex, WebRequest request){
        ApiError error = apiErrorBuilder(ex, HttpStatus.UNPROCESSABLE_ENTITY);

        return handleExceptionInternal(ex, error, new HttpHeaders(), error.getStatus(), request);
    }

    @ExceptionHandler(NullEntityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleNullEntityException(NullEntityException ex, WebRequest request){
        ApiError error = apiErrorBuilder(ex, HttpStatus.BAD_REQUEST);

        return handleExceptionInternal(ex, error, new HttpHeaders(), error.getStatus(), request);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleDuplicateEmailException(DuplicateEmailException ex, WebRequest request){
        ApiError error = apiErrorBuilder(ex, HttpStatus.CONFLICT);

        return handleExceptionInternal(ex, error, new HttpHeaders(), error.getStatus(), request);
    }


    @ExceptionHandler(UserRegistrationErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleUserRegistrationErrorException(UserRegistrationErrorException ex, WebRequest request){
        ApiError error = apiErrorBuilder(ex, HttpStatus.BAD_REQUEST);

        return handleExceptionInternal(ex, error, new HttpHeaders(), error.getStatus(), request);
    }

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<Object> handleInvalidRequestException(InvalidRequestException ex) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                   ex.getErrors()

            );
    }

    @ExceptionHandler(ValidationFieldException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<FieldErrorDTO> handleValidationFieldException(ValidationFieldException ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getFieldErrorDTO());
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
