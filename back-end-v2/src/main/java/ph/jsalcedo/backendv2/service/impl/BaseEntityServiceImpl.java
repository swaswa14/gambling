package ph.jsalcedo.backendv2.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ph.jsalcedo.backendv2.dto.BaseDto;
import ph.jsalcedo.backendv2.entity.BaseEntity;
import ph.jsalcedo.backendv2.exceptions.EntityDoesNotExistsException;
import ph.jsalcedo.backendv2.exceptions.NullEntityException;
import ph.jsalcedo.backendv2.mapper.EntityToDtoMapper;
import ph.jsalcedo.backendv2.repository.BaseEntityRepository;
import ph.jsalcedo.backendv2.service.BaseEntityService;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseEntityServiceImpl <T extends BaseEntity, R extends BaseDto,
        S extends EntityToDtoMapper<T,R>> implements BaseEntityService<T,R> {
    protected  BaseEntityRepository<T> repository;
    protected  S mapper;

    public BaseEntityServiceImpl(BaseEntityRepository<T> repository, S mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    @Override
    public List<R> findAll(int page, int size, String field) {
        return repository.findAll(createPageable(page,size,field))
                .stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    @Override
    public R findById(Long id) {
        return mapper.apply(repository.findById(id).orElseThrow(()-> new EntityDoesNotExistsException(id)));
    }

    @Override
    public R updateEntity(Long id, T t) {
        //checks first if it exists or else it will throw;
        findById(id);
        return mapper.apply(repository.save(t));
    }

    @Override
    @Transactional
    public boolean deleteEntity(Long id) {
        findById(id);
        repository.deleteById(id);

        //If it does not exists then the delete is successful!
        return repository.findById(id).isEmpty();
    }

    @Override
    public R create(T t) {
        if(t == null)
            throw new NullEntityException();
        return mapper.apply(repository.save(t));
    }

    @Override
    public T findEntityByDTO(R r) {
        return repository.findById(r.getId()).orElseThrow(()-> new EntityDoesNotExistsException(r.getId()));
    }

    protected static Pageable createPageable(int page, int size, String field){
        return PageRequest.of(page, size, Sort.by(field).and(Sort.by("id").descending()));
    }
}
