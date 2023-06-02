package ph.cdo.backend.repository;

import org.springframework.stereotype.Repository;
import ph.cdo.backend.entity.user.Admin;

@Repository
public interface AdminRepository extends UserRepository<Admin> {
}
