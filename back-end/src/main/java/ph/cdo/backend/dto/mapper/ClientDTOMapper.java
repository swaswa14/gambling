package ph.cdo.backend.dto.mapper;

import org.springframework.stereotype.Service;
import ph.cdo.backend.dto.ClientDTO;
import ph.cdo.backend.entity.user.Client;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Function;

@Service
public class ClientDTOMapper extends UserDTOMapper implements Function<Client, ClientDTO> {

    @Override
    public ClientDTO apply(Client client) {
        return new ClientDTO(
                client.getId(),
                client.getRole(),
                client.getEmail(),
                client.getMobilePhone(),
                client.getBalance().setScale(2, RoundingMode.HALF_EVEN)
        );
    }
}
