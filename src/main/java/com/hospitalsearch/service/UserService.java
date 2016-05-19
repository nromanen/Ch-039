package com.hospitalsearch.service;

import com.hospitalsearch.entity.User;
import com.hospitalsearch.util.UserDto;
import com.hospitalsearch.util.UserRegisterDto;

import java.util.List;

public interface UserService {

    void save(User newUser);

    void delete(User user);

    void update(User updatedUser);

    void changeStatus(User user);

    User getById(Long id);

    List<User> getAll();

    User getByEmail(String email);

    List<User> getByRole(long id);

    void register(UserRegisterDto dto);

    void registerUpdate(UserDto dto, String email);

    UserDto getDtoByEmail(String email);


}
