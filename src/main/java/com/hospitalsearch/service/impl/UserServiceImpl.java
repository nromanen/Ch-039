package com.hospitalsearch.service.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hospitalsearch.dao.UserDAO;
import com.hospitalsearch.dto.UserSearchDTO;
import com.hospitalsearch.entity.PatientCard;
import com.hospitalsearch.entity.Role;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.entity.UserDetail;
import com.hospitalsearch.service.PatientCardService;
import com.hospitalsearch.service.RoleService;
import com.hospitalsearch.service.UserService;
import com.hospitalsearch.util.UserDetailRegisterDto;
import com.hospitalsearch.util.UserDto;
import com.hospitalsearch.util.UserRegisterDto;


@Service
public class UserServiceImpl implements UserService {
    private final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDAO dao;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    PatientCardService patientCardService;

    @Override
    public void save(User newUser) {
        try {
            logger.info("save user: " + newUser);
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
            PatientCard patientCard = patientCardService.add(new PatientCard());
            UserDetail userDetail = new UserDetail();
            userDetail.setPatientCard(patientCard);
            newUser.setUserDetails(userDetail);
           /* newUser.getUserRoles().add(roleService.getByType("PATIENT"));*/
            dao.save(newUser);
        } catch (Exception e) {
            logger.error("Error saving user: " + newUser, e);
        }

    }

    @Override
    public void delete(Long id) {
        User user = dao.getById(id);
        try {
            logger.info("delete user " + user);
            for (Role role : user.getUserRoles()) {
                if (role.getType().equals("ADMIN")) return;
            }
            dao.delete(user);
        } catch (Exception e) {
            logger.error("Error delete user: " + user, e);
        }
    }

    @Override
    public void update(User user) {
        try {
            logger.info("update user " + user);
            dao.update(user);
        } catch (Exception e) {
            logger.error("Error update user: " + user, e);
        }
    }


    @Override
    public List<User> searchUser(UserSearchDTO userSearch) {
        return dao.searchUser(userSearch);
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
    public void changeStatus(Long id) {
        dao.changeStatus(id);
    }

    @Override
    public List<User> getAllEnabledUsers() {
        return dao.getAllEnabledUsers();
    }

    @Override
    public List<User> getAllDisabledUsers() {
        return dao.getAllDisabledUsers();
    }

    //Illia
    @Override
    public List<User> getByRole(String role) {
        return dao.getByRole(role);
    }

    @Override
    public List<User> searchByRole(String role, String search) {
        return dao.searchByRole(role, search);
    }
    //Illia

    @Override
    public void register(UserRegisterDto dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
//		user.setLastName(dto.getUserName());
        user.setUserRoles(new HashSet<>(Collections.singletonList(roleService.getByType("PATIENT"))));
        save(user);
    }

    @Override
    public void registerUpdate(UserDetailRegisterDto dto, String email) {
        User user = dao.getByEmail(email);
//		
//		user.setFirstName(dto.getFirstName());
//		user.setLastName(dto.getLastName());
//		user.setPhone(dto.getPhone());
//		user.setGender(dto.getGender());
//		user.setAddress(dto.getAddress());
//		user.setBirthDate(dto.getBirthDate());
//		if (nonNull(dto.getImagePath())) {
//			user.setImagePath(dto.getImagePath());
//		}
        dao.update(user);
    }


    @Override
    public void registerUpdate(UserDto dto, String email) {
        // TODO Auto-generated method stub

    }


    @Override
    public UserDto getDtoByEmail(String email) {
        // TODO Auto-generated method stub
        return null;
    }


}
