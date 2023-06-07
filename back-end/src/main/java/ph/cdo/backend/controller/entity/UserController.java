package ph.cdo.backend.controller.entity;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ph.cdo.backend.dto.DTOEntity;
import ph.cdo.backend.entity.base.User;
import ph.cdo.backend.enums.Role;
import ph.cdo.backend.exceptions.EmptyListException;
import ph.cdo.backend.exceptions.EntityDoesNotExistsException;
import ph.cdo.backend.exceptions.FailedToDeleteException;
import ph.cdo.backend.response.ResponseObject;
import ph.cdo.backend.service.IUserService;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class UserController<T extends User, R extends DTOEntity> {

    private final IUserService<T, R> userService;

    protected UserController(IUserService<T, R> userService) {
        this.userService = userService;
    }


    public ResponseEntity<R> getUser(Long id){
        try {
            R user = userService.retrieve(id);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }catch (EntityDoesNotExistsException e){
            throw new EntityDoesNotExistsException(id);
        }
    }

    public ResponseEntity<ResponseObject> deleteUser(Long id){
        boolean result = userService.deleteById(id);


        if(!result) throw new FailedToDeleteException("user", id);


        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("deleted", true);
        objectMap.put("id", id);
        return ResponseEntity
                .ok(
                        ResponseObject
                                .builder()
                                .mappedBody(objectMap)
                                .build()
                );
    }

//   public ResponseEntity<R> forgotPassword(Long id){
//        R r = userService.forgotPassword(id);
//   }





    public ResponseEntity<List<R>> getAllUsers(){
        try {
            List<R> list = new java.util.ArrayList<>(userService.findAllByRole(Role.Client).stream().toList());
            list.sort(new UserDTPComparator());

            return ResponseEntity.status(HttpStatus.OK).body(list);
        }catch (Exception e){
            throw new EmptyListException("Clients");
        }
    }


    public ResponseEntity<List<R>> getAlLEnabled(){
        try {
            List<R> list = new java.util.ArrayList<>(userService.findAllEnabled().stream().toList());
            list.sort(new UserDTPComparator());

            return ResponseEntity.status(HttpStatus.OK).body(list);
        }catch (Exception e){
            throw new EmptyListException("Enabled User");
        }
    }



    public ResponseEntity<List<R>> getAllDisabled(){
        try {
            List<R> list = new java.util.ArrayList<>(userService.findAllDisabled().stream().toList());
            list.sort(new UserDTPComparator());

            return ResponseEntity.status(HttpStatus.OK).body(list);
        }catch (Exception e){
            throw new EmptyListException("Disabled User");
        }
    }


    public ResponseEntity<List<R>> getAllLocked(){
        try {
            List<R> list = new java.util.ArrayList<>(userService.findAllLocked().stream().toList());
            list.sort(new UserDTPComparator());

            return ResponseEntity.status(HttpStatus.OK).body(list);
        }catch (Exception e){
            throw new EmptyListException("Locked Client");
        }
    }


    public ResponseEntity<List<R>> getAllUnlocked(){
        try {
            List<R> list = new java.util.ArrayList<>(userService.findAllUnlocked().stream().toList());
            list.sort(new UserDTPComparator());

            return ResponseEntity.status(HttpStatus.OK).body(list);
        }catch (Exception e){
            throw new EmptyListException("Unlocked Client");
        }
    }


    private final class UserDTPComparator implements  Comparator<R>{

        @Override
        public int compare(R o1, R o2) {
            return (int) (o2.getID() - o1.getID());
        }
    }


}
