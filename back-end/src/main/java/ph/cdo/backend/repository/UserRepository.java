package ph.cdo.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
import ph.cdo.backend.entity.user.User;
import ph.cdo.backend.enums.Role;

import java.util.List;


@NoRepositoryBean()
public interface UserRepository<T extends User> extends JpaRepository<T, Long> {
    List<T> findByIsEnabledTrue();
    List<T> findByIsEnabledFalse();

    List<T> findByIsLockedTrue();
    List<T> findByIsLockedFalse();

    List<T> findByRole(Role role);

}
