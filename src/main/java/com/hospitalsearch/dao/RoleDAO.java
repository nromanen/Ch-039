package com.hospitalsearch.dao;

import com.hospitalsearch.entity.Role;

/**
 * Created by andrew on 11.05.16.
 */
public interface RoleDAO extends GenericDAO<Role, Long> {

    Role getByType(String type);

}
