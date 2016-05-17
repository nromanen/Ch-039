package com.hospitalsearch.service.impl;

import com.hospitalsearch.dao.UserDAO;
import com.hospitalsearch.entity.PatientCard;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.service.PatientCardService;
import com.hospitalsearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by deplague on 5/11/16.
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDAO dao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PatientCardService patientCardService;

    @Override
    public void save(User newUser) {
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        PatientCard patientCard = patientCardService.add(new PatientCard());
        newUser.setPatientCard(patientCard);
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

    @Override
    public User getByEmail(String email) {
        return dao.getByEmail(email);
    }

    @Override
    public List<User> getByRole(long id) {
        return dao.getByRole(id);
    }

    @Override
    public void changeStatus(User user) {
        dao.changeStatus(user);
    }
}
