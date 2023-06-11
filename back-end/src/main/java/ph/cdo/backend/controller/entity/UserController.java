package ph.cdo.backend.controller.entity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ph.cdo.backend.dto.DTOEntity;
import ph.cdo.backend.entity.base.User;
import ph.cdo.backend.enums.Role;
import ph.cdo.backend.response.ResponseObject;
import ph.cdo.backend.service.impl.user.IUserService;
import ph.cdo.backend.service.TokenService;

import java.util.Comparator;
import java.util.List;

@Slf4j

public abstract class UserController<T extends User, R extends DTOEntity> {

    protected final IUserService<T, R> userService;

    protected final TokenService tokenService;

    protected UserController(IUserService<T, R> userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }


    public ResponseEntity<R> updateUser(Long id, T user){
        log.info("update user API " + user.getClass().getSimpleName());
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(id, user));
    }


    public ResponseEntity<R> getUser(Long id){
        log.info("get user API ID= " + id);
            return ResponseEntity.status(HttpStatus.OK).body(userService.retrieve(id));

    }

   public  ResponseEntity<ResponseObject> confirmEmail(String token){
       log.info("confirm token API TOKEN= " + token );
       return  ResponseEntity.ok(userService.confirmUser(tokenService.findByToken(token)));
   }

    public ResponseEntity<ResponseObject> deleteUser(Long id){
        log.info("delete user API ID= " + id);
        return ResponseEntity.ok(userService.deleteEntity(id));
    }

    public ResponseEntity<List<R>> getAllUsers(int page, int size, String field){
        log.info("get All Users API ");
            List<R> list = new java.util.ArrayList<>(userService.findAllByRole(Role.Client, page, size , field).stream().toList());
            list.sort(new SortUserByLatest());
            return ResponseEntity.status(HttpStatus.OK).body(list);

    }





    public ResponseEntity<List<R>> getAlLEnabled(int page, int size, String field){
        log.info("get All Enabled Users API ");
            List<R> list = userService.findAllEnabled(page, size , field);
            list.sort(new SortUserByLatest());
            return ResponseEntity.status(HttpStatus.OK).body(list);

    }



    public ResponseEntity<List<R>> getAllDisabled(int page, int size, String field){
        log.info("get All Enabled Users API ");
            List<R> list = new java.util.ArrayList<>(userService.findAllDisabled(page, size , field).stream().toList());
            list.sort(new SortUserByLatest());
            return ResponseEntity.status(HttpStatus.OK).body(list);

    }


    public ResponseEntity<List<R>> getAllLocked(int page, int size, String field){
        log.info("get All Enabled Users API ");
            List<R> list = new java.util.ArrayList<>(userService.findAllLocked(page, size , field).stream().toList());
            list.sort(new SortUserByLatest());

            return ResponseEntity.status(HttpStatus.OK).body(list);

    }


    public ResponseEntity<List<R>> getAllUnlocked(int page, int size, String field){
        log.info("get All Users API ");
            List<R> list = new java.util.ArrayList<>(userService.findAllUnlocked(page, size , field).stream().toList());
            list.sort(new SortUserByLatest());

            return ResponseEntity.status(HttpStatus.OK).body(list);

    }


    private final class SortUserByLatest implements  Comparator<R>{

        @Override
        public int compare(R o1, R o2) {
            return (int) (o2.getID() - o1.getID());
        }
    }


}
