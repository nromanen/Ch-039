package com.hospitalsearch.dao;

import com.hospitalsearch.dto.UserSearchDTO;
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
    List<User> getAllEnabledUsers();
    List<User> getAllDisabledUsers();
    List<User> searchUser(UserSearchDTO userSearch);
    //Illia
    List<User> getByRole(String role);

    List<User> searchByRole(String role, String search);
}





