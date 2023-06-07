package ph.cdo.backend.service;

import ph.cdo.backend.dto.DTOEntity;
import ph.cdo.backend.entity.base.User;
import ph.cdo.backend.enums.Role;
import ph.cdo.backend.exceptions.EntityDoesNotExistsException;

import java.util.List;


public interface IUserService<T extends User, R extends DTOEntity> {

    R save(T user);

    R retrieve(Long id) throws EntityDoesNotExistsException;

    List<R> retrieve();

    R update(Long id, T t);

    boolean deleteById(Long id);
    boolean deleteUser(T t);


    boolean isEmailTaken(String email);


    List<R> findAllEnabled();
    List<R> findAllDisabled();

    List<R> findAllLocked();
    List<R> findAllUnlocked();

    List<R> findAllByRole(Role role);

    void forgotPassword(Long id);

    R changePassword(Long id, String newPassword);
}
