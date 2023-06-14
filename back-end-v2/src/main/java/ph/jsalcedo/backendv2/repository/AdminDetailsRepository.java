package ph.jsalcedo.backendv2.repository;

import org.springframework.stereotype.Repository;
import ph.jsalcedo.backendv2.entity.user.details.AdminDetails;

import java.util.Optional;

@Repository("AdminRepository")
public interface AdminDetailsRepository extends AbstractUserDetailsRepository<AdminDetails> {

    Optional<AdminDetails> findByAgentCode(String code);
}
