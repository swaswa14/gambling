package ph.cdo.backend.controller.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ph.cdo.backend.dto.records.ClientDTOEntity;
import ph.cdo.backend.dto.records.TransactionDTO;
import ph.cdo.backend.entity.user.Client;
import ph.cdo.backend.request.ClientDepositRequest;
import ph.cdo.backend.request.ClientWithdrawalRequest;
import ph.cdo.backend.response.ResponseObject;
import ph.cdo.backend.service.ClientService;
import ph.cdo.backend.service.TokenService;
import ph.cdo.backend.service.TransactionService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/client")
public class ClientController extends UserController<Client, ClientDTOEntity> {
    private final ClientService clientService;
    private final TransactionService transactionService;

    @Autowired
    protected ClientController(@Qualifier("ClientService")ClientService clientService, TokenService tokenService, TransactionService transactionService) {
        super(clientService, tokenService);
        this.clientService =clientService;

        this.transactionService = transactionService;
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
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ClientDTOEntity> updateUser(@PathVariable Long id, @RequestBody Client user) {
        return super.updateUser(id, user);
    }


    @PutMapping("/deposit/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED) //todo test
    public ResponseEntity<ClientDTOEntity> depositUser(@PathVariable Long id, @RequestBody ClientDepositRequest request){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(clientService.deposit(id, request));
    }

    @PutMapping("/witdhraw/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED) // todo test
    public ResponseEntity<ClientDTOEntity> withdraw(@PathVariable Long id, @RequestBody ClientWithdrawalRequest request){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(clientService.withdraw(id, request));
    }

    @GetMapping("/transactions/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TransactionDTO>> findAllTransaction(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.findAllByClientID(id));
    }


}
