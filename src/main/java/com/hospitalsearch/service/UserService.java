package com.hospitalsearch.service;

import com.hospitalsearch.entity.User;
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
    @Transactional(readOnly=true,propagation= Propagation.SUPPORTS)
    User getById(Long id);
    @Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
    List<User> getAll();
}
