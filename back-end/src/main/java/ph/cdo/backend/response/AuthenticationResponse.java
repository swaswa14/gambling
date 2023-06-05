package ph.cdo.backend.response;

import lombok.Builder;
import lombok.Data;
import ph.cdo.backend.enums.Role;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
public class AuthenticationResponse implements IResponseBody{
    private String token;
    private Role role;

    private String getToken() {
        return token;
    }

    private Role getRole() {
        return role;
    }

    @Override
    public ResponseObject getResponse() {
         Map<String, Object> objectMap = new HashMap<>();
         objectMap.put("token", getToken());
         objectMap.put("role", getRole());

         return ResponseObject.builder().mappedBody(objectMap).build();
    }
}
