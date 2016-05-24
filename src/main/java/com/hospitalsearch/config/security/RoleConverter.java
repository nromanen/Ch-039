package com.hospitalsearch.config.security;

import com.hospitalsearch.entity.Role;
import com.hospitalsearch.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by andrew on 11.05.16.
 */
@Component
public class RoleConverter implements Converter<Object, Role> {

    @Autowired
    RoleService roleService;

    @Override
    //get role by type
    public Role convert(Object element) {
        String type =( String) element;
        Role role = roleService.getByType(type);
        System.out.println("Role " + role);
        return role;
    }
}
