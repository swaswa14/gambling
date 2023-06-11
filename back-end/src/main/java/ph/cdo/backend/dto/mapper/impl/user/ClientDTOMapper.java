package ph.cdo.backend.dto.mapper.impl.user;

import org.springframework.stereotype.Service;
import ph.cdo.backend.dto.records.ClientDTOEntity;
import ph.cdo.backend.entity.user.Client;

import java.math.RoundingMode;

@Service("ClientDTOMapper")
public  class ClientDTOMapper  extends UserDTOMapper<Client, ClientDTOEntity> {

    @Override
    public ClientDTOEntity apply(Client client) {
        return new ClientDTOEntity(
                client.getId(),
                client.getRole(),
                client.getEmail(),
                client.getMobilePhone(),
                client.getBalance().setScale(2, RoundingMode.HALF_EVEN),
                client.getName()
        );
    }
}
