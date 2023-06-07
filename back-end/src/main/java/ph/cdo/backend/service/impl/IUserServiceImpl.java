package ph.cdo.backend.service.impl;

import ph.cdo.backend.dto.DTOEntity;
import ph.cdo.backend.dto.mapper.impl.UserDTOMapper;
import ph.cdo.backend.entity.base.User;
import ph.cdo.backend.enums.Role;
import ph.cdo.backend.exceptions.EntityDoesNotExistsException;
import ph.cdo.backend.exceptions.NullEntityException;
import ph.cdo.backend.repository.noBean.UserRepository;
import ph.cdo.backend.service.IUserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


//todo ADD error handling !!!!
public class IUserServiceImpl<T extends User, R extends DTOEntity, S extends UserDTOMapper<T, R>> implements IUserService<T, R> {

    protected final S userDTOMapper;

    protected final UserRepository<T> userRepository; //TODO FIX Just name a bean!!

    public IUserServiceImpl(S userDTOMapper, UserRepository<T> userRepository) {
        this.userDTOMapper = userDTOMapper;
        this.userRepository = userRepository;
    }

    @Override
    public R save(T user) {
        if(user == null){
            throw new NullEntityException();
        }

        return userDTOMapper.apply(userRepository.save(user));
    }

    @Override
    public R retrieve(Long id) {
        return userDTOMapper.apply(userRepository.findById(id)
                .orElseThrow(()-> new EntityDoesNotExistsException(id)));
    }

    @Override
    public List<R> retrieve() {
        return userRepository.findAll()
                .stream()
                .map(userDTOMapper)
                .collect(Collectors.toList());

    }

    @Override
    public R update(Long id, T t) {
        if(t.getId() == null) {
            throw new EntityDoesNotExistsException(id);
        }
        userRepository.findById(id).orElseThrow(()-> new EntityDoesNotExistsException(id));
        return userDTOMapper.apply(userRepository.save(t));
    }

    @Override
    public boolean deleteById(Long id) {
        Optional<T> optional;
        try{

            userRepository.deleteById(id);

            optional = userRepository.findById(id);

        }catch (Exception e){
            System.out.println("error catch! " + e.toString());
            e.printStackTrace();
            return false;
        }

        return optional.isEmpty();



    }

    @Override
    public boolean deleteUser(T t) {

        Optional<T> optional;
       try{
           Long id = t.getId();
           userRepository.delete(t);

           optional = userRepository.findById(id);

       }catch (Exception e){
           System.out.println("error catch! " + e.toString());
           e.printStackTrace();
           return false;
       }

       return optional.isEmpty();

    }

    @Override
    public boolean isEmailTaken(String email) {
        return !userRepository.findAllByEmail(email.toLowerCase()).isEmpty();
    }

    @Override
    public List<R> findAllEnabled() {
        return userRepository
                .findByIsEnabledTrue()
                .stream()
                .map(userDTOMapper)
                .collect(Collectors.toList());

    }

    @Override
    public List<R> findAllDisabled() {
        return userRepository
                .findByIsEnabledFalse()
                .stream()
                .map(userDTOMapper)
                .collect(Collectors.toList());
    }

    @Override
    public List<R> findAllLocked() {
        return userRepository
                .findByIsLockedTrue()
                .stream()
                .map(userDTOMapper)
                .collect(Collectors.toList());
    }

    @Override
    public List<R> findAllUnlocked() {
        return userRepository
                .findByIsLockedFalse()
                .stream()
                .map(userDTOMapper)
                .collect(Collectors.toList());
    }

    @Override
    public List<R> findAllByRole(Role role) {
        return userRepository
                .findByRole(role)
                .stream()
                .map(userDTOMapper)
                .collect(Collectors.toList());
    }

    @Override
    public void forgotPassword(Long id) {

    }

    @Override
    public R changePassword(Long id, String newPassword) {
        return null;
    }


}
