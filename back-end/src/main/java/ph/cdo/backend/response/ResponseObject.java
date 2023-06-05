package ph.cdo.backend.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public final class ResponseObject {
    private  Map<String, Object> mappedBody;

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(mappedBody);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }


}
