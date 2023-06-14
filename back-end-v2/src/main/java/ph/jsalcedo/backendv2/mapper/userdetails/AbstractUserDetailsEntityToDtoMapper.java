package ph.jsalcedo.backendv2.mapper.userdetails;

import ph.jsalcedo.backendv2.dto.user.details.AbstractUserDetailsDto;
import ph.jsalcedo.backendv2.entity.user.details.AbstractUserDetails;
import ph.jsalcedo.backendv2.mapper.EntityToDtoMapper;

public abstract class AbstractUserDetailsEntityToDtoMapper<T extends AbstractUserDetails, R extends AbstractUserDetailsDto> extends EntityToDtoMapper<T, R> {
    protected abstract R createDto();
    @Override
    @SuppressWarnings("unchecked")
    public R apply(T t) {
        R dto = createDto();
        dto.setId(t.getId());
        dto.setEmail(t.getBasicInformation().getEmail());
        dto.setFirstname(t.getBasicInformation().getName().getFirstname());
        dto.setLastname(t.getBasicInformation().getName().getLastname());
        dto.setPhone(t.getBasicInformation().getPhone().toString());
        dto.setBirthday(t.getBasicInformation().getBirthday());
        return dto;
    }
}
