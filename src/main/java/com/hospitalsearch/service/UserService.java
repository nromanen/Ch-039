package com.hospitalsearch.service;

import java.util.List;

import com.hospitalsearch.dto.UserAdminDTO;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hospitalsearch.entity.User;
import com.hospitalsearch.util.UserDetailRegisterDto;
import com.hospitalsearch.util.UserDto;
import com.hospitalsearch.dto.UserRegisterDto;
@Transactional
public interface UserService {

    void save(User newUser);

    void delete(Long id);

    void updateUser(User user);

    void update(User user);

    void changeStatus(Long id);

    @Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
    User getById(Long id);

    @Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
    List<User> getAll();

    @Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
    List<User> getUsers(UserAdminDTO userAdminDTO);

    @Transactional(readOnly=true,propagation= Propagation.SUPPORTS)
    List<User> searchUser(UserAdminDTO userAdminDTO);

    @Transactional(readOnly=true,propagation= Propagation.SUPPORTS)
    User getByEmail(String email);

    void register(UserRegisterDto dto);

    void registerUpdate(UserDto dto, String email);

    UserDto getDtoByEmail(String email);

	void registerUpdate(UserDetailRegisterDto dto, String email);

    //Illia
    List<User> getByRole(String role, int pageNumber, int pageSize, String sortBy, Boolean order);
    List<User> getByRole(String role);
    Long countOfUsersByRole(String role);
    Long countOfUsersByRole(String role, String search);
    List<User> searchByRole(String role, String search,int pageNumber, int pageSize, String sortBy, Boolean order);
    Integer pageCount(Long countOfItems, int itemsPerPage);
}
