package ph.cdo.backend.service.impl.base_entity;

import org.springframework.stereotype.Service;
import ph.cdo.backend.dto.DTOEntity;
import ph.cdo.backend.dto.mapper.BaseEntityDTOMapper;
import ph.cdo.backend.entity.base.BaseEntity;
import ph.cdo.backend.exceptions.EntityDoesNotExistsException;
import ph.cdo.backend.repository.noBean.BaseEntityRepository;
import ph.cdo.backend.service.BaseEntityService;

import java.util.List;
import java.util.stream.Collectors;


public abstract class BaseEntityServiceImpl<T extends BaseEntity, R extends DTOEntity, S extends BaseEntityDTOMapper<T, R>> implements BaseEntityService<T, R> {

    protected final BaseEntityRepository<T> repository;
    protected final S mapper;

    public BaseEntityServiceImpl(BaseEntityRepository<T> repository, S mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    @Override
    public List<R> findAll() {
        return repository
                .findAll()
                .stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    @Override
    public R findById(Long id) throws EntityDoesNotExistsException {
        return mapper.apply(repository.findById(id).orElseThrow(()-> new EntityDoesNotExistsException(id)));
    }

    @Override
    public R updateEntity(Long id, T t) throws EntityDoesNotExistsException {
        //checks first if it exists or else it will throw;
        findById(id);
        return mapper.apply(repository.save(t));
    }

    @Override
    public boolean deleteEntity(Long id) throws EntityDoesNotExistsException {
        //checks first if it exists or else it will throw;
        findById(id);
        repository.deleteById(id);


        //If it does not exists then the delete is successful!
        return repository.findById(id).isEmpty();
    }

    @Override
    public R create(T t) {
       return mapper.apply(repository.save(t));
    }

    @Override
    public T findEntityByDTO(R r) {
        return repository.findById(r.getID()).orElseThrow(()-> new EntityDoesNotExistsException(r.getID()));
    }
}
