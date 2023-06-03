package ph.cdo.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ph.cdo.backend.entity.user.User;
import ph.cdo.backend.enums.Role;
import ph.cdo.backend.errors.EntityDoesNotExistsException;
import ph.cdo.backend.errors.NullEntityException;
import ph.cdo.backend.repository.UserRepository;
import ph.cdo.backend.service.IUserService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
//todo ADD error handling !!!!
public class IUserServiceImpl<T extends User> implements IUserService<T> {
    private final UserRepository<T> userRepository;
    @Override
    public T save(T user) {
        if(user == null){
            throw new NullEntityException();
        }

        return userRepository.save(user);
    }

    @Override
    public T retrieve(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new EntityDoesNotExistsException(id));
    }

    @Override
    public List<T> retrieve() {
        return userRepository.findAll();
    }

    @Override
    public T update(Long id, T t) {
        if(t.getId() == null) {
            throw new EntityDoesNotExistsException(id);
        }
        return userRepository.save(t);
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
    public List<T> findAllEnabled() {
        return userRepository.findByIsEnabledTrue();
    }

    @Override
    public List<T> findAllDisabled() {
        return userRepository.findByIsEnabledFalse();
    }

    @Override
    public List<T> findAllLocked() {
        return userRepository.findByIsLockedTrue();
    }

    @Override
    public List<T> findAllUnlocked() {
        return userRepository.findByIsLockedFalse();
    }

    @Override
    public List<T> findAllByRole(Role role) {
        return userRepository.findByRole(role);
    }
}
