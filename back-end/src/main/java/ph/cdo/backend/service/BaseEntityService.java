package ph.cdo.backend.service;

import ph.cdo.backend.dto.DTOEntity;
import ph.cdo.backend.entity.base.BaseEntity;
import ph.cdo.backend.exceptions.EntityDoesNotExistsException;

import java.util.List;

public interface BaseEntityService<T extends BaseEntity, R extends DTOEntity> {

    List<R> findAll();

    R findById(Long id) throws EntityDoesNotExistsException;

    R updateEntity(Long id, T t) throws EntityDoesNotExistsException;

    boolean deleteEntity(Long id) throws EntityDoesNotExistsException;

    R create (T t);

    T findEntityByDTO(R r);


}
