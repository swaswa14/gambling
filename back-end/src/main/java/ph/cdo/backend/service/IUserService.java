package ph.cdo.backend.service;

import org.springframework.stereotype.Service;
import ph.cdo.backend.entity.user.Client;
import ph.cdo.backend.entity.user.User;
import ph.cdo.backend.enums.Role;

import java.util.List;


public interface IUserService<T extends User, R> {

    R save(T user);

    R retrieve(Long id);

    List<R> retrieve();

    R update(Long id, T t);

    boolean deleteById(Long id);
    boolean deleteUser(T t);


    List<R> findAllEnabled();
    List<R> findAllDisabled();

    List<R> findAllLocked();
    List<R> findAllUnlocked();

    List<R> findAllByRole(Role role);
}
