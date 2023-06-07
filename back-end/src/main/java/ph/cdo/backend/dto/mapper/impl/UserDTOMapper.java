package ph.cdo.backend.dto.mapper.impl;


import ph.cdo.backend.dto.DTOEntity;
import ph.cdo.backend.entity.base.User;

import java.util.function.Function;


public abstract class UserDTOMapper<T extends User, R extends DTOEntity> implements Function<T,R>{

    @Override
    public R apply(T t) {
        return null;
    }

}
