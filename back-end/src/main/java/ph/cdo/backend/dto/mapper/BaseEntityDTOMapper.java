package ph.cdo.backend.dto.mapper;

import ph.cdo.backend.dto.DTOEntity;
import ph.cdo.backend.entity.base.BaseEntity;

import java.util.function.Function;

public class BaseEntityDTOMapper <T extends BaseEntity, R extends DTOEntity> implements Function<T,R> {
    @Override
    public R apply(T t) {
        return null;
    }
}
