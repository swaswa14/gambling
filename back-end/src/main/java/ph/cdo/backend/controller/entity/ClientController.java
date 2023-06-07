package ph.cdo.backend.controller.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ph.cdo.backend.dto.records.ClientDTOEntity;
import ph.cdo.backend.entity.user.Client;
import ph.cdo.backend.service.ClientService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/client")
public class ClientController extends UserController<Client, ClientDTOEntity> {

    @Autowired
    protected ClientController(ClientService clientService) {
        super(clientService);
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
}
