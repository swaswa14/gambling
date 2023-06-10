package ph.cdo.backend.exceptions;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ph.cdo.backend.dto.records.FieldErrorDTO;
import ph.cdo.backend.service.ApiErrorService;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final ApiErrorService service;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        FieldErrorDTO fieldErrorDTO = service.fieldErrorDTOFunction(bindingResult);
        return new ResponseEntity<>(fieldErrorDTO, HttpStatus.UNPROCESSABLE_ENTITY);
    }


    @ExceptionHandler(EntityDoesNotExistsException.class)
    public ResponseEntity<ApiError> handleEntityDoesNotExistsException(
            EntityDoesNotExistsException ex){
        return new ResponseEntity<>(service.apiErrorBuilder(ex, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidValueException.class)
    public ResponseEntity<ApiError> handleInvalidValueException(
            InvalidValueException ex){
        return new ResponseEntity<>(service.apiErrorBuilder(ex, HttpStatus.UNPROCESSABLE_ENTITY), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(EmptyListException.class)
    public ResponseEntity<ApiError> handleEmptyListException(
            EmptyListException ex, WebRequest request){
        return new ResponseEntity<>(service.apiErrorBuilder(ex, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NullEntityException.class)
    public ResponseEntity<ApiError> handleNullEntityException(
            NullEntityException ex, WebRequest request){
        return new ResponseEntity<>(service.apiErrorBuilder(ex, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ApiError> handleDuplicateEmailException(
            DuplicateEmailException ex, WebRequest request){
        return new ResponseEntity<>(service.apiErrorBuilder(ex, HttpStatus.CONFLICT), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserRegistrationErrorException.class)
    public ResponseEntity<ApiError> handleUserRegistrationErrorException(
            UserRegistrationErrorException ex, WebRequest request){
        ApiError error = service.apiErrorBuilder(ex, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<List<ApiError>> handleInvalidRequestException(
            InvalidRequestException ex) {
        return new ResponseEntity<>(ex.getErrors(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationFieldException.class)
    public ResponseEntity<FieldErrorDTO> handleValidationFieldException(
            ValidationFieldException ex){
        return new ResponseEntity<>(ex.getFieldErrorDTO(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(AccountNotEnabledException.class)
    public ResponseEntity<ApiError> handleAccountNotEnabledException(
            AccountNotEnabledException ex){
        return new ResponseEntity<>(service.apiErrorBuilder(ex, HttpStatus.FORBIDDEN), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(CustomBadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentialsException(
            CustomBadCredentialsException ex){
        return new ResponseEntity<>(service.apiErrorBuilder(ex, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ApiError> handleTokenExpiredException(
            TokenExpiredException ex){
        return new ResponseEntity<>(service.apiErrorBuilder(ex, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ApiError> handleAccountNotFoundException(
            AccountNotFoundException ex){
        return new ResponseEntity<>(service.apiErrorBuilder(ex, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FailedToDeleteException.class)
    public ResponseEntity<ApiError> handleFailedToDeleteException(
            FailedToDeleteException ex){
        return new ResponseEntity<>(service.apiErrorBuilder(ex, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ApiError> handleInsufficientFunds(
            InsufficientFundsException ex){
        return new ResponseEntity<>(service.apiErrorBuilder(ex, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }
}
