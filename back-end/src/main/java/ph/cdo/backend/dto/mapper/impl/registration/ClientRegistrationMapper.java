package ph.cdo.backend.dto.mapper.impl.registration;

import org.springframework.stereotype.Service;
import ph.cdo.backend.entity.base.Name;
import ph.cdo.backend.entity.user.Client;
import ph.cdo.backend.enums.Role;
import ph.cdo.backend.request.registration.ClientBasicRegistrationForm;

import java.math.BigDecimal;

@Service("ClientRegistrationMapper")
public class ClientRegistrationMapper extends UserRegistrationMapper<Client, ClientBasicRegistrationForm> {
    @Override
    public Client apply(ClientBasicRegistrationForm request) {
        Client client = super.apply(request);
        client.setBalance(BigDecimal.valueOf(0L));
        client.setInvitationCode(request.getInvitationCode().isBlank() ? "N/A" : request.getInvitationCode());
        client.setRole(Role.Client);
        return client;
    }
}
