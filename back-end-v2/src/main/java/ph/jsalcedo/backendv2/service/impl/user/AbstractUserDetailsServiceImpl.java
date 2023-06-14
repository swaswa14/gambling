package ph.jsalcedo.backendv2.service.impl.user;

import jakarta.transaction.Transactional;
import ph.jsalcedo.backendv2.dto.user.details.AbstractUserDetailsDto;

import ph.jsalcedo.backendv2.entity.user.User;
import ph.jsalcedo.backendv2.entity.user.details.AbstractUserDetails;
import ph.jsalcedo.backendv2.exceptions.EntityDoesNotExistsException;

import ph.jsalcedo.backendv2.exceptions.NullEntityException;
import ph.jsalcedo.backendv2.mapper.userdetails.AbstractUserDetailsEntityToDtoMapper;

import ph.jsalcedo.backendv2.repository.AbstractUserDetailsRepository;

import ph.jsalcedo.backendv2.service.AbstractUserDetailsService;
import ph.jsalcedo.backendv2.service.impl.BaseEntityServiceImpl;

public abstract class AbstractUserDetailsServiceImpl <T extends AbstractUserDetails, R extends AbstractUserDetailsDto,
        S extends AbstractUserDetailsEntityToDtoMapper<T,R>> extends BaseEntityServiceImpl<T,R,S> implements AbstractUserDetailsService<T,R> {

    protected AbstractUserDetailsRepository<T> repository;
    public AbstractUserDetailsServiceImpl(AbstractUserDetailsRepository<T> repository, S mapper) {
        super(repository, mapper);
        this.repository = repository;

    }

    @Override
    public R findByEmail(String email) {
        return mapper.apply(repository.findByBasicInformationEmail(email)
                .orElseThrow(()-> new EntityDoesNotExistsException(email)));

    }

    @Override
    public R findByUser(User user) {
        if(user == null)
            throw new NullEntityException();
        return mapper.apply(repository.findByUser(user).orElseThrow(()-> new EntityDoesNotExistsException(user.getId())));
    }

    @Override
    @Transactional
    public R addUser(T details, User user) {
        if(user == null)
            throw new NullEntityException();
        details.setUser(user);

        return mapper.apply(repository.save(details));
    }

    @Override
    public R findByUserId(Long id) {
        if(id == null)
            throw new NullEntityException();
        return mapper.apply(repository.findByUserId(id).orElseThrow(()-> new EntityDoesNotExistsException(id)));
    }
}
