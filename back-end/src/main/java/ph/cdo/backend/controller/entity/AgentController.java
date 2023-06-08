package ph.cdo.backend.controller.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ph.cdo.backend.dto.records.AgentDTOEntity;
import ph.cdo.backend.entity.user.Agent;
import ph.cdo.backend.response.ResponseObject;
import ph.cdo.backend.service.AgentService;
import ph.cdo.backend.service.TokenService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/agent")
public class AgentController extends UserController<Agent, AgentDTOEntity> {


    protected AgentController(
            @Autowired @Qualifier("AgentService") AgentService agentService,
            @Autowired TokenService tokenService) {
        super(agentService,tokenService);
    }

    @Override
    @GetMapping("agents")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<AgentDTOEntity>> getAllUsers() {
        return super.getAllUsers();
    }

    @Override
    @GetMapping("/confirm/{token}")
    public ResponseEntity<ResponseObject> confirmEmail(@PathVariable String token) {
        return super.confirmEmail(token);
    }

    @Override
    @GetMapping("agents/enabled")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<AgentDTOEntity>> getAlLEnabled() {
        return super.getAlLEnabled();
    }

    @Override
    @GetMapping("agents/disabled")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<AgentDTOEntity>> getAllDisabled() {
        return super.getAllDisabled();
    }

    @Override
    @GetMapping("agents/locked")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<AgentDTOEntity>> getAllLocked() {
        return super.getAllLocked();
    }

    @Override
    @GetMapping("agents/unlocked")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<AgentDTOEntity>> getAllUnlocked() {
        return super.getAllUnlocked();
    }
}
