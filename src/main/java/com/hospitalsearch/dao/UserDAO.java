package com.hospitalsearch.dao;

import com.hospitalsearch.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by deplague on 5/11/16.
 */
@Component
public interface UserDAO extends GenericDAO<User,Long>{

    User getByEmail(String email);
    void changeStatus(User user);
    List<User> getByRole(long id);
}
