package com.hospitalsearch.service.impl;

import com.google.common.collect.ImmutableMap;
import com.hospitalsearch.dao.UserDAO;
import com.hospitalsearch.entity.Role;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.service.RoleService;
import com.hospitalsearch.service.UserService;
import com.hospitalsearch.service.mapper.UserBeanMapper;
import com.hospitalsearch.util.UserDto;
import com.hospitalsearch.util.UserRegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static java.util.Objects.nonNull;


@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO dao;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserBeanMapper userMapper;

    @Autowired
    private MailService mailService;


    @Override
    public void save(User newUser) {
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        dao.save(newUser);
    }

    @Override
    public void delete(User user) {
        for (Role role : user.getUserRoles()) {
            if (role.getType().equals("ADMIN")) {
                return;
            }
        }
        dao.delete(user);
    }

    @Override
    public void update(User updatedUser) {
        dao.update(updatedUser);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public User getById(Long id) {
        return dao.getById(id);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<User> getAll() {
        return dao.getAll();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public User getByEmail(String email) {
        return dao.getByEmail(email);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<User> getByRole(long id) {
        return dao.getByRole(id);
    }

    @Override
    public void changeStatus(User user) {
        dao.changeStatus(user);
    }

    @Override
    public void register(UserRegisterDto dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setLastName(dto.getUserName());
        user.setUserRoles(new HashSet<>(Collections.singletonList(roleService.getByType("PATIENT"))));
        save(user);
        mailService.createMessage("receiverEmail@gmail.com", "subject", "registerInvite.vm",
                ImmutableMap.of("user", dto.getUserName()));
    }

    @Override
    public void registerUpdate(UserDto dto, String email) {
        User user = dao.getByEmail(email);
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhone(dto.getPhone());
        user.setGender(dto.getGender());
        user.setAddress(dto.getAddress());
        user.setBirthDate(dto.getBirthDate());
        if (nonNull(dto.getImagePath())) {
            user.setImagePath(dto.getImagePath());
        }
        dao.update(user);
    }

    @Override
    public UserDto getDtoByEmail(String email) {
        return userMapper.convertToBean(getByEmail(email));
    }
}
