package com.hospitalsearch.service;

import com.hospitalsearch.dto.UserSearchDTO;
import com.hospitalsearch.entity.Hospital;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.util.UserDetailRegisterDto;
import com.hospitalsearch.util.UserDto;
import com.hospitalsearch.util.UserRegisterDto;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
public interface UserService {

    void save(User newUser);

    void delete(Long id);

    void update(User updatedUser);

    void changeStatus(Long id);

    @Transactional(readOnly=true,propagation= Propagation.SUPPORTS)
    User getById(Long id);

    @Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
    List<User> getAll();

    @Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
    List<User> getAllEnabledUsers();

    @Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
    List<User> getAllDisabledUsers();

    @Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
    User getByEmail(String email);

    void register(UserRegisterDto dto);

    void registerUpdate(UserDto dto, String email);

    UserDto getDtoByEmail(String email);

	void registerUpdate(UserDetailRegisterDto dto, String email);

    public List<User> searchUser(UserSearchDTO userSearch);

    //Illia
    List<User> getByRole(String role);

    List<User> searchByRole(String role, String search);
}
