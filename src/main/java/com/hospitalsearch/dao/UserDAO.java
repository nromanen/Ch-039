package com.hospitalsearch.dao;

import com.hospitalsearch.dto.UserFilterDTO;
import com.hospitalsearch.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Andrew Jasinskiy
 */
@Component
public interface UserDAO extends GenericDAO<User,Long>{

    User getByEmail(String email);
    void changeStatus(long id);
    Boolean emailExists(String email);
    void updateUser(User user);

    List<User> getUsers(UserFilterDTO userFilterDTO);
    List<User> searchUser(UserFilterDTO userFilterDTO);
    //Illia
    List<User> getByRole(String role, int pageNumber, int pageSize, String sortBy, Boolean order);
    List<User> getByRole(String role);
    Long countOfUsersByRole(String role);
    Long countOfUsersByRole(String role, String search);
    List<User> searchByRole(String role, String search, int pageNumber, int pageSize, String sortBy, Boolean order);
}





