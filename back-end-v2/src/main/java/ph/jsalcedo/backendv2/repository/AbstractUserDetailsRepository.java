package ph.jsalcedo.backendv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ph.jsalcedo.backendv2.entity.user.User;
import ph.jsalcedo.backendv2.entity.user.details.AbstractUserDetails;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface AbstractUserDetailsRepository<T extends AbstractUserDetails> extends BaseEntityRepository<T> {
    Optional<T> findByBasicInformationEmail(String email);

    Optional<T> findByUserId(Long id);
    Optional<T> findByUser(User user);
    Optional<T> findByUserUsername(String email);
}
