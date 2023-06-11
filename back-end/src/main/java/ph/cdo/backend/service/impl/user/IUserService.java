package ph.cdo.backend.service.impl.user;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import ph.cdo.backend.dto.DTOEntity;
import ph.cdo.backend.entity.Token;
import ph.cdo.backend.entity.base.User;
import ph.cdo.backend.enums.Role;
import ph.cdo.backend.exceptions.EmailErrorException;
import ph.cdo.backend.exceptions.EntityDoesNotExistsException;
import ph.cdo.backend.response.ResponseObject;

import java.util.List;


public interface IUserService<T extends User, R extends DTOEntity> extends  UserDetailsService {

    R save(T user);

    R retrieve(Long id) throws EntityDoesNotExistsException;

    List<R> retrieve(int page, int size, String field);

    R update(Long id, T t);

    boolean deleteById(Long id);
    ResponseObject deleteEntity(Long id);
    boolean deleteUser(T t);


    boolean isEmailTaken(String email);


    List<R> findAllEnabled(int page, int size, String field);
    List<R> findAllDisabled(int page, int size, String field);

    List<R> findAllLocked(int page, int size, String field);
    List<R> findAllUnlocked(int page, int size, String field);

    List<R> findAllByRole(Role role, int page, int size, String field);

    void forgotPassword(Long id);

    R changePassword(Long id, String newPassword);

    ResponseObject confirmUser(Token token);
}
