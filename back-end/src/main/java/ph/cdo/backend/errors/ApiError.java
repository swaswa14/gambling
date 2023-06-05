package ph.cdo.backend.errors;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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
        return String.format("""
                Status : %s
                Status code : %s
                Error message : %s
                Time stamp : %s
                Exception name : %s """,
                status.name(),
                statusCode,
                errorMessage,
                timeStamp.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                exception
                );
    }
}
