package ph.jsalcedo.backendv2.repository;

import org.springframework.stereotype.Repository;
import ph.jsalcedo.backendv2.entity.user.User;
import ph.jsalcedo.backendv2.model.Role;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


//JPA test
@Repository("UserRepository")
public interface UserRepository extends BaseEntityRepository<User>{
    Optional<User> findByUsername(String username);

    List<User> findAllByRole(Role role, Pageable pageable);

    List<User> findAllByEnabledTrue(Pageable pageable);
    List<User> findAllByEnabledFalse(Pageable pageable);

    List<User> findAllByLockedTrue(Pageable pageable);
    List<User> findAllByLockedFalse(Pageable pageable);
}
