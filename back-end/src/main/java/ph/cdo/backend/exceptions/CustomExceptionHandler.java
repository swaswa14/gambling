package ph.cdo.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ph.cdo.backend.dto.records.FieldErrorDTO;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(EntityDoesNotExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiError> handleEntityDoesNotExistsException(
            EntityDoesNotExistsException ex){
        ApiError error = apiErrorBuilder(ex, HttpStatus.NOT_FOUND);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(InvalidValueException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<ApiError> handleInvalidValueException(
            InvalidValueException ex, WebRequest request){
        ApiError error = apiErrorBuilder(ex, HttpStatus.UNPROCESSABLE_ENTITY);

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

    @ExceptionHandler(EmptyListException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiError> handleEmptyListException(InvalidValueException ex, WebRequest request){
        ApiError error = apiErrorBuilder(ex, HttpStatus.NOT_FOUND);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(NullEntityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleNullEntityException(NullEntityException ex, WebRequest request){
        ApiError error = apiErrorBuilder(ex, HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ApiError> handleDuplicateEmailException(DuplicateEmailException ex, WebRequest request){
        ApiError error = apiErrorBuilder(ex, HttpStatus.CONFLICT);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }


    @ExceptionHandler(UserRegistrationErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleUserRegistrationErrorException(UserRegistrationErrorException ex, WebRequest request){
        ApiError error = apiErrorBuilder(ex, HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
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

    @ExceptionHandler(DisabledException.class)
    @ResponseStatus(HttpStatus.LOCKED)
    public ResponseEntity<ApiError> handleDisabledException(DisabledException ex){
        ApiError error = apiErrorBuilder(ex, HttpStatus.LOCKED);
        return ResponseEntity.status(HttpStatus.LOCKED).body(error);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleBadCredentialsException(BadCredentialsException ex){
        ApiError error = apiErrorBuilder(ex, HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(TokenExpiredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleTokenExpiredException(TokenExpiredException ex){
        ApiError error = apiErrorBuilder(ex, HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


    @ExceptionHandler(FailedToDeleteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleFailedToDeleteException(FailedToDeleteException ex){
        ApiError error = apiErrorBuilder(ex, HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
