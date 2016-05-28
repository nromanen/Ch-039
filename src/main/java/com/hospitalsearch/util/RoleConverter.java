
package com.hospitalsearch.util;

import com.hospitalsearch.entity.Role;
import com.hospitalsearch.service.RoleService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


/**
 * @author Andrew Jasinskiy
 */

@Component
public class RoleConverter implements Converter<Object, Role> {
    private final Logger logger = LogManager.getLogger(RoleConverter.class);
    @Autowired
    RoleService roleService;

    @Override
    //get role by type
    public Role convert(Object element) {
        String type =(String) element;
        Role role = roleService.getByType(type);
        System.out.println("Role " + role);
        return role;
    }
}
