package com.hospitalsearch.dao;

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
    List<User> getByRole(long id);
    Boolean emailExists(String email);
}





