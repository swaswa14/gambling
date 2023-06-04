package ph.cdo.backend.dto.mapper;


import org.springframework.stereotype.Service;
import ph.cdo.backend.dto.ClientDTO;
import ph.cdo.backend.dto.DTOEntity;
import ph.cdo.backend.entity.user.Client;
import ph.cdo.backend.entity.user.User;

import java.util.function.Function;


public abstract class UserDTOMapper<T extends User, R extends DTOEntity> implements Function<T,R>{

    @Override
    public R apply(T t) {
        return null;
    }

}
