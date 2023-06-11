package ph.cdo.backend.repository.noBean;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
import ph.cdo.backend.entity.base.User;
import ph.cdo.backend.enums.Role;


import java.util.List;
import java.util.Optional;


@NoRepositoryBean
public interface UserRepository<T extends User> extends JpaRepository<T, Long> {
    List<T> findAllByIsEnabledTrue(Pageable pageable);
    List<T> findAllByIsEnabledFalse(Pageable pageable);

    List<T> findAllByIsLockedTrue(Pageable pageable);
    List<T> findAllByIsLockedFalse(Pageable pageable);

    List<T> findAllByRole(Role role, Pageable pageable);

    List<T> findAllByEmail(String email); //this is a mistake!!!


    Optional<T> findByEmail(String email);



}
