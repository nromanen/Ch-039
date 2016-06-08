package com.hospitalsearch.service;

import com.hospitalsearch.entity.Role;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Andrew Jasinskiy
 */
@Transactional
public interface RoleService {

    List<Role> getAll();
    Role getByType(String role);
    Role getById(long id);
}
