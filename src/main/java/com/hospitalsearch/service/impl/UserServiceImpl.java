package com.hospitalsearch.service.impl;

import com.hospitalsearch.dao.UserDAO;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by deplague on 5/11/16.
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDAO dao;

    @Override
    public void save(User newUser) {
        dao.save(newUser);
    }

    @Override
    public void delete(User user) {
        dao.delete(user);
    }

    @Override
    public void update(User updatedUser) {
        dao.update(updatedUser);
    }

    @Override
    public User getById(Long id) {
        return dao.getById(id);
    }

    @Override
    public List<User> getAll() {
        return dao.getAll();
    }
}
