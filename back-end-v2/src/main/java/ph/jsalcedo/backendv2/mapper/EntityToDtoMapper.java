package ph.jsalcedo.backendv2.mapper;

import ph.jsalcedo.backendv2.dto.BaseDto;
import ph.jsalcedo.backendv2.entity.BaseEntity;

import java.util.function.Function;

public  class   EntityToDtoMapper<T extends BaseEntity, R extends BaseDto> implements Function<T, R> {


    @Override
    public R apply(T t) {
        return null;
    }
}
