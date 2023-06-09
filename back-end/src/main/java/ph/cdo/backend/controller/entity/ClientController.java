package ph.cdo.backend.controller.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ph.cdo.backend.dto.records.ClientDTOEntity;
import ph.cdo.backend.entity.Token;
import ph.cdo.backend.entity.base.User;
import ph.cdo.backend.entity.user.Client;
import ph.cdo.backend.response.ResponseObject;
import ph.cdo.backend.service.ClientService;
import ph.cdo.backend.service.IUserService;
import ph.cdo.backend.service.TokenService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/client")
public class ClientController extends UserController<Client, ClientDTOEntity> {

    @Autowired
    protected ClientController(ClientService clientService, TokenService tokenService) {
        super(clientService, tokenService);

    }


    @Override
    @GetMapping("/confirm/{token}")
    public ResponseEntity<ResponseObject> confirmEmail(@PathVariable String token) {
        return super.confirmEmail(token);
    }

    @Override
    @GetMapping("clients") // s so that it calls clients. Being consistent to plural forms
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ClientDTOEntity>> getAllUsers() {
        return super.getAllUsers();
    }



    @Override
    @GetMapping("clients/enabled")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ClientDTOEntity>> getAlLEnabled() {
        return super.getAlLEnabled();
    }

    @Override
    @GetMapping("clients/disabled")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ClientDTOEntity>> getAllDisabled() {
        return super.getAllDisabled();
    }

    @Override
    @GetMapping("clients/locked")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ClientDTOEntity>> getAllLocked() {
        return super.getAllLocked();
    }

    @Override
    @GetMapping("clients/unlocked")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ClientDTOEntity>> getAllUnlocked() {
        return super.getAllUnlocked();
    }




    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ClientDTOEntity> getUser(@PathVariable Long id) {
        return super.getUser(id);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseObject> deleteUser(@PathVariable Long id) {
        return super.deleteUser(id);
    }

    @Override
    @DeleteMapping("/update/{id}")
    public ResponseEntity<ClientDTOEntity> updateUser(@PathVariable Long id, @RequestBody Client user) {
        return super.updateUser(id, user);
    }
}
