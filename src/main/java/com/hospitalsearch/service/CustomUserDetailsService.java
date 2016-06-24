package com.hospitalsearch.service;

import com.hospitalsearch.entity.Role;
import com.hospitalsearch.entity.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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
 * @author Andrew Jasinskiy
 */
@Service("CustomUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService{

    private final Logger logger = LogManager.getLogger(CustomUserDetailsService.class);

    @Autowired
    private UserService userService;

    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.getByEmail(email.toLowerCase());
        logger.info("User : " + user.getEmail());
        if (user == null) {
            logger.warn("User not found!");
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
        return authorities;
    }
}
