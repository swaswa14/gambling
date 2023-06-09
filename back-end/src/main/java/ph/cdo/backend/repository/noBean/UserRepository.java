package ph.cdo.backend.repository.noBean;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ph.cdo.backend.entity.base.User;
import ph.cdo.backend.enums.Role;

import java.util.List;
import java.util.Optional;


@NoRepositoryBean
public interface UserRepository<T extends User> extends JpaRepository<T, Long> {
    List<T> findByIsEnabledTrue();
    List<T> findByIsEnabledFalse();

    List<T> findByIsLockedTrue();
    List<T> findByIsLockedFalse();

    List<T> findByRole(Role role);

    List<T> findAllByEmail(String email);

    Optional<T> findByEmail(String email);

}
