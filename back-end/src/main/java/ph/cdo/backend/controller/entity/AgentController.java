package ph.cdo.backend.controller.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ph.cdo.backend.dto.records.AgentDTOEntity;
import ph.cdo.backend.dto.records.ClientDTOEntity;
import ph.cdo.backend.entity.user.Agent;
import ph.cdo.backend.entity.user.Client;
import ph.cdo.backend.response.ResponseObject;
import ph.cdo.backend.service.AgentService;
import ph.cdo.backend.service.TokenService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/agent")
@CrossOrigin
public class AgentController extends UserController<Agent, AgentDTOEntity> {


    protected AgentController(
            @Autowired @Qualifier("AgentService") AgentService agentService,
            @Autowired TokenService tokenService) {
        super(agentService,tokenService);
    }

    @Override
    @GetMapping("agents")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<AgentDTOEntity>> getAllUsers(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String field
    ) {
        return super.getAllUsers(page, size, field);
    }

    @Override
    @GetMapping("/confirm/{token}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseObject> confirmEmail(@PathVariable String token) {
        return super.confirmEmail(token);
    }

    @Override
    @GetMapping("/agents/enabled")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<AgentDTOEntity>> getAlLEnabled(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String field
    ) {
        return super.getAlLEnabled(page, size, field);
    }

    @Override
    @GetMapping("agents/disabled")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<AgentDTOEntity>> getAllDisabled(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String field
    ) {
        return super.getAllDisabled(page, size, field);
    }

    @Override
    @GetMapping("agents/locked")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<AgentDTOEntity>> getAllLocked(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String field
    ) {
        return super.getAllLocked(page, size, field);
    }

    @Override
    @GetMapping("agents/unlocked")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<AgentDTOEntity>> getAllUnlocked(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String field
    ) {
        return super.getAllUnlocked(page, size, field);
    }


    @Override
    @DeleteMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AgentDTOEntity> updateUser(@PathVariable Long id, @RequestBody Agent user) {
        return super.updateUser(id, user);
    }
    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AgentDTOEntity> getUser(@PathVariable Long id) {
        return super.getUser(id);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseObject> deleteUser(@PathVariable Long id) {
        return super.deleteUser(id);
    }
}
