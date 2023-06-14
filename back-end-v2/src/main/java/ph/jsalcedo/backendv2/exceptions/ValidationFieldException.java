//package ph.jsalcedo.backendv2.exceptions;
//
//import lombok.Getter;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import ph.cdo.backend.dto.records.FieldErrorDTO;
//
//@Getter
//@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
//public class ValidationFieldException extends RuntimeException{
//    private final FieldErrorDTO fieldErrorDTO;
//
//    public ValidationFieldException(String message, FieldErrorDTO fieldErrorDTO) {
//        super(message);
//        this.fieldErrorDTO = fieldErrorDTO;
//    }
//}
