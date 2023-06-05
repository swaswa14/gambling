package ph.cdo.backend.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientRegistrationResponse implements IResponseBody{
    String header;
    String body;
    String email;
    String footer;
    String token;

    private String getHeader() {
        return header;
    }

    private String getBody() {
        return body;
    }

    private String getEmail() {
        return email;
    }

    private String getFooter() {
        return footer;
    }

    private String getToken() {
        return token;
    }



    @Override
    public ResponseObject getResponse() {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("header", getHeader());
        objectMap.put("body", getBody());
        objectMap.put("email", getEmail());
        objectMap.put("footer", getFooter());

        return ResponseObject.builder().mappedBody(objectMap).build();
    }
}
