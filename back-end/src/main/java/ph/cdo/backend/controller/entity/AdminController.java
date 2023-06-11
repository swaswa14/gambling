package ph.cdo.backend.controller.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ph.cdo.backend.dto.records.AdminDTOEntity;
import ph.cdo.backend.dto.records.AgentDTOEntity;
import ph.cdo.backend.entity.user.Admin;
import ph.cdo.backend.entity.user.Agent;
import ph.cdo.backend.response.ResponseObject;
import ph.cdo.backend.service.AdminService;
import ph.cdo.backend.service.TokenService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@CrossOrigin
public class AdminController extends UserController<Admin, AdminDTOEntity> {
    @Autowired
    protected AdminController(
            @Qualifier("AdminService")AdminService adminService,
            TokenService tokenService) {
        super(adminService, tokenService);
    }

    @Override
    @GetMapping("/confirm/{token}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseObject> confirmEmail(@PathVariable String token) {
        return super.confirmEmail(token);
    }
    @Override
    public ResponseEntity<List<AdminDTOEntity>> getAllUsers(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String field
    ) {
        return super.getAllUsers(page, size , field);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("admins/enabled")
    public ResponseEntity<List<AdminDTOEntity>> getAlLEnabled(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String field
    ) {
        return super.getAlLEnabled(page, size , field);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("admins/disabled")
    public ResponseEntity<List<AdminDTOEntity>> getAllDisabled(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String field
    ){
        return super.getAllDisabled(page, size , field);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("admins/locked")
    public ResponseEntity<List<AdminDTOEntity>> getAllLocked(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String field
    ) {
        return super.getAllLocked(page, size , field);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("admins/unlocked")
    public ResponseEntity<List<AdminDTOEntity>> getAllUnlocked(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String field
    ) {
        return super.getAllUnlocked(page, size , field);
    }

    @Override
    @DeleteMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AdminDTOEntity> updateUser(@PathVariable Long id, @RequestBody Admin user) {
        return super.updateUser(id, user);
    }
    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AdminDTOEntity> getUser(@PathVariable Long id) {
        return super.getUser(id);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseObject> deleteUser(@PathVariable Long id) {
        return super.deleteUser(id);
    }
}
