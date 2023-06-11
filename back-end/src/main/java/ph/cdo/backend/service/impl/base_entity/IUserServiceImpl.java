package ph.cdo.backend.service.impl.base_entity;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ph.cdo.backend.dto.DTOEntity;
import ph.cdo.backend.dto.mapper.impl.user.UserDTOMapper;
import ph.cdo.backend.entity.Token;
import ph.cdo.backend.entity.base.User;
import ph.cdo.backend.enums.Role;
import ph.cdo.backend.exceptions.*;
import ph.cdo.backend.repository.noBean.UserRepository;
import ph.cdo.backend.response.ResponseObject;
import ph.cdo.backend.service.EmailService;
import ph.cdo.backend.service.impl.user.IUserService;

import java.util.*;
import java.util.stream.Collectors;


//todo ADD error handling !!!!
@Slf4j

public abstract class IUserServiceImpl<T extends User, R extends DTOEntity, S extends UserDTOMapper<T, R>> implements IUserService<T, R> {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(()-> new EntityDoesNotExistsException(username));
    }

    protected final S userDTOMapper;

    protected final UserRepository<T> userRepository; //TODO FIX Just name a bean!!

    protected final EmailService emailService;


    public IUserServiceImpl(S userDTOMapper, UserRepository<T> userRepository, EmailService emailService) {
        this.userDTOMapper = userDTOMapper;
        this.userRepository = userRepository;

        this.emailService = emailService;
    }

    @Override
    public R save(T user){
        if(user == null){
            throw new NullEntityException();
        }

        return userDTOMapper.apply(userRepository.save(user));
    }

    @Override
    public R retrieve(Long id) {
        return userDTOMapper.apply(userRepository.findById(id)
                .orElseThrow(()-> new EntityDoesNotExistsException(id)));
    }

    @Override
    public List<R> retrieve(int page, int size, String field) {
        return userRepository.findAll(createPageable(page,size,field))
                .stream()
                .map(userDTOMapper)
                .collect(Collectors.toList());

    }



    @Override
    public R update(Long id, T t) {
        if(t.getId() == null) {
            throw new EntityDoesNotExistsException(id);
        }
        userRepository.findById(id).orElseThrow(()-> new EntityDoesNotExistsException(id));
        return userDTOMapper.apply(userRepository.save(t));
    }

    @Override
    public boolean deleteById(Long id) {
        Optional<T> optional;
        try{

            userRepository.deleteById(id);

            optional = userRepository.findById(id);

        }catch (Exception e){
            System.out.println("error catch! " + e.toString());
            e.printStackTrace();
             throw new FailedToDeleteException("User", id);
        }

        return optional.isEmpty();
    }

    @Override
    public ResponseObject deleteEntity(Long id) {
        boolean result = deleteById(id);
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("deleted", result);
        objectMap.put("id", id);
        return ResponseObject.builder()
                .mappedBody(objectMap)
                .build();
    }

    @Override
    public boolean deleteUser(T t) {

        Optional<T> optional;
       try{
           Long id = t.getId();
           userRepository.delete(t);

           optional = userRepository.findById(id);

       }catch (Exception e){
           System.out.println("error catch! " + e.toString());
           e.printStackTrace();
           throw new FailedToDeleteException("User", t.getId());
       }

       return optional.isEmpty();

    }

    @Override
    public boolean isEmailTaken(String email) {
        return !userRepository.findAllByEmail(email.toLowerCase()).isEmpty();
    }

    @Override
    public List<R> findAllEnabled(int page, int size, String field) {


        return userRepository
                .findAllByIsEnabledTrue(createPageable(page,size,field))
                .stream()
                .map(userDTOMapper)
                .collect(Collectors.toList());

    }

    @Override
    public List<R> findAllDisabled(int page, int size, String field) {
        return userRepository
                .findAllByIsEnabledFalse(createPageable(page,size,field))
                .stream()
                .map(userDTOMapper)
                .collect(Collectors.toList());
    }

    @Override
    public List<R> findAllLocked(int page, int size, String field) {

        return userRepository
                .findAllByIsLockedTrue(createPageable(page,size,field))
                .stream()
                .map(userDTOMapper)
                .collect(Collectors.toList());
    }

    @Override
    public List<R> findAllUnlocked(int page, int size, String field) {
        return userRepository
                .findAllByIsLockedFalse(createPageable(page,size,field))
                .stream()
                .map(userDTOMapper)
                .collect(Collectors.toList());
    }

    @Override
    public List<R> findAllByRole(Role role, int page, int size, String field) {
        return userRepository
                .findAllByRole(role, createPageable(page,size,field))
                .stream()
                .map(userDTOMapper)
                .collect(Collectors.toList());
    }

    @Override
    public void forgotPassword(Long id) {

    }

    @Override
    public R changePassword(Long id, String newPassword) {
        return null;
    }


    @Override
    public ResponseObject confirmUser(Token token) {
        if(new Date().after(token.getExpiryDate()))
            throw new TokenExpiredException(token);
        T user = (T) token.getUser();
        user.setEnabled(true);

        T savedUser = userRepository.save(user);
        Map<String,Object> objectMap = new HashMap<>();
        objectMap.put("success", savedUser.isEnabled());
        objectMap.put("email", user.getEmail());
        objectMap.put("token", token.getToken());
        return ResponseObject.builder()
                .mappedBody(objectMap)
                .build();
    }


    private static Pageable createPageable(int page, int size, String field){
        return PageRequest.of(page, size, Sort.by(field).and(Sort.by("id").descending()));
    }


}
