package ph.cdo.backend.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {
    private HttpStatus status;
    private String errorMessage;

    private String exception;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss M/d/yyyy")
    private ZonedDateTime timeStamp;

    private String statusCode;

    @Override
    public String toString() {
        return String.format(
                """
                        Error message : %s
                        """,
                    formatErrorMessage(errorMessage)
                );
    }


    private  String formatErrorMessage(String errorMessage) {
        if (errorMessage == null || errorMessage.length() == 0) {
            return errorMessage;
        }

        // Make the first character to upper case
        errorMessage = errorMessage.substring(0, 1).toUpperCase() + errorMessage.substring(1);

        // Add period at the end if it's not there already
        if (!errorMessage.endsWith(".")) {
            errorMessage += ".";
        }

        return errorMessage;
    }
}
