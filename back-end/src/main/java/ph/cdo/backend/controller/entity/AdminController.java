package ph.cdo.backend.controller.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ph.cdo.backend.dto.records.AdminDTOEntity;
import ph.cdo.backend.entity.user.Admin;
import ph.cdo.backend.service.AdminService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController extends UserController<Admin, AdminDTOEntity> {
    @Autowired
    protected AdminController(AdminService adminService) {
        super(adminService);
    }

    @Override
    public ResponseEntity<List<AdminDTOEntity>> getAllUsers() {
        return super.getAllUsers();
    }

    @Override
    public ResponseEntity<List<AdminDTOEntity>> getAlLEnabled() {
        return super.getAlLEnabled();
    }

    @Override
    public ResponseEntity<List<AdminDTOEntity>> getAllDisabled() {
        return super.getAllDisabled();
    }

    @Override
    public ResponseEntity<List<AdminDTOEntity>> getAllLocked() {
        return super.getAllLocked();
    }

    @Override
    public ResponseEntity<List<AdminDTOEntity>> getAllUnlocked() {
        return super.getAllUnlocked();
    }
}
