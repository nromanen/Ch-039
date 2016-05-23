package com.hospitalsearch.service.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hospitalsearch.dao.UserDAO;
import com.hospitalsearch.entity.Role;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.service.RoleService;
import com.hospitalsearch.service.UserService;
import com.hospitalsearch.util.UserDetailRegisterDto;
import com.hospitalsearch.util.UserDto;
import com.hospitalsearch.util.UserRegisterDto;


@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDAO dao;

	@Autowired
	private RoleService roleService;

	@Autowired
	private PasswordEncoder passwordEncoder;


	@Override
	public void save(User newUser) {
		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		//        PatientCard patientCard = patientCardService.add(new PatientCard());
		//  newUser.setPatientCard(patientCard);
		dao.save(newUser);
	}

	@Override
	public void delete(User user) {
		for(Role role : user.getUserRoles()){
			if(role.getType().equals("ADMIN")){
				return;
			}
		}
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
