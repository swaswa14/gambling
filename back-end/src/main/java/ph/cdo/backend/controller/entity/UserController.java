package ph.cdo.backend.controller.entity;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ph.cdo.backend.dto.DTOEntity;
import ph.cdo.backend.entity.Token;
import ph.cdo.backend.entity.base.User;
import ph.cdo.backend.enums.Role;
import ph.cdo.backend.exceptions.EmptyListException;
import ph.cdo.backend.exceptions.EntityDoesNotExistsException;
import ph.cdo.backend.exceptions.FailedToDeleteException;
import ph.cdo.backend.response.ResponseObject;
import ph.cdo.backend.service.IUserService;
import ph.cdo.backend.service.TokenService;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class UserController<T extends User, R extends DTOEntity> {

    protected final IUserService<T, R> userService;

    protected final TokenService tokenService;

    protected UserController(IUserService<T, R> userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }





    public ResponseEntity<R> getUser(Long id){
            return ResponseEntity.status(HttpStatus.OK).body(userService.retrieve(id));

    }

   public  ResponseEntity<ResponseObject> confirmEmail(String token){
       return  ResponseEntity.ok(userService.confirmUser(tokenService.findByToken(token)));
   }

    public ResponseEntity<ResponseObject> deleteUser(Long id){
        return ResponseEntity.ok(userService.deleteEntity(id));
    }

    public ResponseEntity<List<R>> getAllUsers(){
            List<R> list = new java.util.ArrayList<>(userService.findAllByRole(Role.Client).stream().toList());
            list.sort(new UserDTPComparator());
            return ResponseEntity.status(HttpStatus.OK).body(list);

    }





    public ResponseEntity<List<R>> getAlLEnabled(){
            List<R> list = new java.util.ArrayList<>(userService.findAllEnabled().stream().toList());
            list.sort(new UserDTPComparator());
            return ResponseEntity.status(HttpStatus.OK).body(list);

    }



    public ResponseEntity<List<R>> getAllDisabled(){
            List<R> list = new java.util.ArrayList<>(userService.findAllDisabled().stream().toList());
            list.sort(new UserDTPComparator());
            return ResponseEntity.status(HttpStatus.OK).body(list);

    }


    public ResponseEntity<List<R>> getAllLocked(){
            List<R> list = new java.util.ArrayList<>(userService.findAllLocked().stream().toList());
            list.sort(new UserDTPComparator());

            return ResponseEntity.status(HttpStatus.OK).body(list);

    }


    public ResponseEntity<List<R>> getAllUnlocked(){
            List<R> list = new java.util.ArrayList<>(userService.findAllUnlocked().stream().toList());
            list.sort(new UserDTPComparator());

            return ResponseEntity.status(HttpStatus.OK).body(list);

    }


    private final class UserDTPComparator implements  Comparator<R>{

        @Override
        public int compare(R o1, R o2) {
            return (int) (o2.getID() - o1.getID());
        }
    }


}
