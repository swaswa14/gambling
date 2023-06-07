package ph.cdo.backend.repository;

import org.springframework.stereotype.Repository;
import ph.cdo.backend.entity.user.Admin;
import ph.cdo.backend.repository.noBean.UserRepository;

@Repository("AdminRepository")
public interface AdminRepository extends UserRepository<Admin> {
}
