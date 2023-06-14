package ph.jsalcedo.backendv2.service;

import ph.jsalcedo.backendv2.dto.BaseDto;
import ph.jsalcedo.backendv2.entity.BaseEntity;

import java.util.List;

public interface BaseEntityService <T extends BaseEntity, R extends BaseDto>{
    List<R> findAll(int page, int size, String field);

    R findById(Long id);

    R updateEntity(Long id, T t);

    boolean deleteEntity(Long id);

    R create (T t);
    T findEntityByDTO(R r);
}
