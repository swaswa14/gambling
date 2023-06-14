package ph.jsalcedo.backendv2.mapper.userdetails;

import org.springframework.stereotype.Service;
import ph.jsalcedo.backendv2.dto.user.details.AbstractUserDetailsDto;
import ph.jsalcedo.backendv2.dto.user.details.ClientDetailsDto;
import ph.jsalcedo.backendv2.entity.user.details.ClientDetails;
@Service("ClientEntityDtoMapper")
//todo test
public class ClientEntityDtoMapper extends AbstractUserDetailsEntityToDtoMapper<ClientDetails, ClientDetailsDto>{
    @Override
    protected ClientDetailsDto createDto() {
        return new ClientDetailsDto();
    }

    @Override
    public ClientDetailsDto apply(ClientDetails clientDetails) {
        ClientDetailsDto dto  =  super.apply(clientDetails);
        dto.setBalance(clientDetails.getBalance());
        return dto;
    }
}
