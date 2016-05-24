package com.hospitalsearch.service;



import com.hospitalsearch.entity.Role;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by andrew on 11.05.16.
 */
@Transactional
public interface RoleService {

    List<Role> getAll();

    Role getByType(String role);

    Role getById(long id);

}
