package com.hospitalsearch.service;

import java.util.List;

import com.hospitalsearch.dto.UserFilterDTO;
import com.hospitalsearch.dto.UserRegisterDTO;
import com.hospitalsearch.exception.ResetPasswordException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hospitalsearch.entity.User;
import com.hospitalsearch.util.UserDetailRegisterDto;
import com.hospitalsearch.util.UserDto;

@Transactional
public interface UserService {

    void save(User newUser);

    void delete(Long id);

    void update(User user);

    void changeStatus(Long id);

    @Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
    User getById(Long id);

    @Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
    List<User> getAll();

    @Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
    List<User> getUsers(UserFilterDTO userFilterDTO);

    @Transactional(readOnly=true,propagation= Propagation.SUPPORTS)
    List<User> searchUser(UserFilterDTO userFilterDTO);

    @Transactional(readOnly=true,propagation= Propagation.SUPPORTS)
    User getByEmail(String email);

    User register(UserRegisterDTO dto);

    void registerUpdate(UserDto dto, String email);

    UserDto getDtoByEmail(String email);

    void resetPassword(String email, String newPassword) throws ResetPasswordException;

    void registerUpdate(UserDetailRegisterDto dto, String email);

    //Illia
    List<User> getByRole(String role, int pageNumber, int pageSize, String sortBy, Boolean order);
    List<User> getByRole(String role);
    Long countOfUsersByRole(String role);
    Long countOfUsersByRole(String role, String search);
    List<User> searchByRole(String role, String search,int pageNumber, int pageSize, String sortBy, Boolean order);
    Integer pageCount(Long countOfItems, int itemsPerPage);
}
