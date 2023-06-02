package ph.cdo.backend.service;

import org.springframework.stereotype.Service;
import ph.cdo.backend.entity.user.Client;
import ph.cdo.backend.entity.user.User;
import ph.cdo.backend.enums.Role;

import java.util.List;


public interface IUserService<T extends User> {

    T save(T client);

    T retrieve(Long id);

    List<T> retrieve();

    T update(Long id, T t);

    boolean deleteById(Long id);
    boolean deleteUser(T t);


    List<T> findAllEnabled();
    List<T> findAllDisabled();

    List<T> findAllLocked();
    List<T> findAllUnlocked();

    List<T> findAllByRole(Role role);
}
