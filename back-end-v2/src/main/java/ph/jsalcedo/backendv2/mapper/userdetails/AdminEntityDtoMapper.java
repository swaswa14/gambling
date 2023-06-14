package ph.jsalcedo.backendv2.mapper.userdetails;

import org.springframework.stereotype.Service;
import ph.jsalcedo.backendv2.dto.user.details.AdminDetailsDto;
import ph.jsalcedo.backendv2.entity.user.details.AdminDetails;
@Service("AdminEntityDtoMapper")
//todo test
public class AdminEntityDtoMapper extends AgentEntityDtoMapper<AdminDetails, AdminDetailsDto>{

    @Override
    protected AdminDetailsDto createDto() {
        return new AdminDetailsDto();
    }

    @Override
    public AdminDetailsDto apply(AdminDetails adminDetails) {
        AdminDetailsDto dto =  super.apply(adminDetails);
        dto.setPosition(adminDetails.getPosition());
        return dto;
    }
}
