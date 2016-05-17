package com.hospitalsearch.service;

import com.hospitalsearch.entity.User;
import com.hospitalsearch.util.UserDetailRegisterDto;
import com.hospitalsearch.util.UserRegisterDto;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by deplague on 5/11/16.
 */
@Transactional
public interface UserService {
    void save(User newUser);
    void delete(User user);
    void update(User updatedUser);

    void changeStatus(User user);

    @Transactional(readOnly=true,propagation= Propagation.SUPPORTS)
    User getById(Long id);
    @Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
    List<User> getAll();
    @Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
    User getByEmail(String email);
    @Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
    List<User> getByRole(long id);

    void register(UserRegisterDto dto);

    void registerUpdate(UserDetailRegisterDto dto, String email);


}
