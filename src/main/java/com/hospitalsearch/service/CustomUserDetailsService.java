package com.hospitalsearch.service;

import com.hospitalsearch.entity.Role;
import com.hospitalsearch.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrew on 10.05.16.
 */
@Service("CustomUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private UserService userService;

    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.getByEmail(email);
        System.out.println("User : " + user);
        if (user == null) {
            System.out.println("User not found!");
            throw new UsernameNotFoundException("User with email " + email + " not found!");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                user.getEnabled(), true, true, true, getGrantedAuthorities(user));
    }

    private List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role userRole : user.getUserRoles()) {
            System.out.println("UserRole : " + userRole);
            authorities.add(new SimpleGrantedAuthority("ROLE_" + userRole.getType()));
        }
        System.out.print("authorities :" + authorities);
        return authorities;
    }
}
