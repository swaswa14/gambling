package ph.cdo.backend.controller.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ph.cdo.backend.dto.records.AdminDTOEntity;
import ph.cdo.backend.entity.user.Admin;
import ph.cdo.backend.response.ResponseObject;
import ph.cdo.backend.service.AdminService;
import ph.cdo.backend.service.TokenService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController extends UserController<Admin, AdminDTOEntity> {
    @Autowired
    protected AdminController(
            @Qualifier("AdminService")AdminService adminService,
            TokenService tokenService) {
        super(adminService, tokenService);
    }

    @Override
    @GetMapping("/confirm/{token}")
    public ResponseEntity<ResponseObject> confirmEmail(@PathVariable String token) {
        return super.confirmEmail(token);
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
