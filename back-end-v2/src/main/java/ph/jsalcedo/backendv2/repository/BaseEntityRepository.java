package ph.jsalcedo.backendv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ph.jsalcedo.backendv2.entity.BaseEntity;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseEntityRepository<T extends BaseEntity> extends JpaRepository<T, Long> {
}
