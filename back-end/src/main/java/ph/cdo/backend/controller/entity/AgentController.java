package ph.cdo.backend.controller.entity;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ph.cdo.backend.dto.records.AgentDTOEntity;
import ph.cdo.backend.entity.user.Agent;
import ph.cdo.backend.service.AgentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/agent")
public class AgentController extends UserController<Agent, AgentDTOEntity> {


    protected AgentController(AgentService agentService) {
        super(agentService);
    }

    @Override
    @GetMapping("agents")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<AgentDTOEntity>> getAllUsers() {
        return super.getAllUsers();
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
