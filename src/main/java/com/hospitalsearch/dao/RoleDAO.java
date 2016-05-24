package com.hospitalsearch.dao;

import com.hospitalsearch.entity.Role;
import org.springframework.stereotype.Component;

/**
 * @author Andrew Jasinskiy
 */
@Component
public interface RoleDAO extends GenericDAO<Role, Long> {

    Role getByType(String type);

}
