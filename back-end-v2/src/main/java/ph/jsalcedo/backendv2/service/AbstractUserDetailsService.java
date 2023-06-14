package ph.jsalcedo.backendv2.service;

import ph.jsalcedo.backendv2.dto.user.details.AbstractUserDetailsDto;
import ph.jsalcedo.backendv2.entity.user.User;
import ph.jsalcedo.backendv2.entity.user.details.AbstractUserDetails;
import ph.jsalcedo.backendv2.model.Name;

public interface AbstractUserDetailsService<T extends AbstractUserDetails, R extends AbstractUserDetailsDto> extends BaseEntityService<T,R>{

    R findByEmail(String email);

    R findByUser(User user);

    R addUser(T details, User user);

    R findByUserId(Long id);




}
