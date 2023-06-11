package ph.cdo.backend.dto.mapper.impl.registration;

import ph.cdo.backend.entity.base.Name;
import ph.cdo.backend.entity.base.User;
import ph.cdo.backend.request.registration.BasicRegistrationForm;

import java.util.function.Function;

public abstract class UserRegistrationMapper <T extends User, R extends BasicRegistrationForm> implements Function<R, T> {

    @Override
    @SuppressWarnings("unchecked")
    public T apply(R r) {
        return (T)User.builder()
                .email(r.getEmail())
                .password(r.getPassword())
                .mobilePhone(r.getMobilePhone().isBlank() ?  "N/A" : r.getMobilePhone() )
                .isEnabled(false)
                .name(Name
                        .builder()
                        .firstName(r.getFirstName())
                        .lastName(r.getLastName())
                        .build())
                .build();

    }
}
