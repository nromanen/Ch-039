package com.hospitalsearch.controller;

import com.hospitalsearch.entity.Role;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.service.RoleService;
import com.hospitalsearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by andrew on 10.05.16.
 */
@Controller
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;
/*
    @RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
    public String homePage(ModelMap model) {
        model.addAttribute("greeting", "Hi, Welcome to mysite");
        return "welcome";
    }*/

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        return "admin/adminTest";
    }

    @RequestMapping(value = "/admin/dashboard", method = RequestMethod.GET)
    public String adminDashboard(ModelMap model) {

        List<User> users = userService.getAll();

        model.addAttribute("user", getPrincipal());
        model.addAttribute("users", users);
        return "admin/dashboard";
    }

    @RequestMapping(value = "/db", method = RequestMethod.GET)
    public String dbaPage(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        return "dba";
    }

    @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        return "403";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
        /*return "redirect:/login?logout";*/
    }

    @RequestMapping(value = "/newUser", method = RequestMethod.GET)
    public String newRegistration(ModelMap model) {

       /* UserForm userForm = new UserForm();*/
        User user = new User();
        model.addAttribute("user", user);
        return "newuser";
    }

    @RequestMapping(value = "/newUser", method = RequestMethod.POST)
    public String saveRegistration(@Valid User user, BindingResult result, ModelMap model) {

        System.out.println(user);

        if (result.hasErrors()) {
            System.out.println("There are errors");
            return "newuser";
        }

        userService.save(user);
        System.out.println("Email: "+user.getEmail());
        System.out.println("Password : "+user.getPassword());
        System.out.println("Status : "+user.getEnabled());
        System.out.println("Roles  : "+user.getUserRoles());
        System.out.println("Checking UserRoles....");
        if(user.getUserRoles()!=null){
            for(Role userRole : user.getUserRoles()){
                System.out.println("Role : "+ userRole.getType());
            }
        }
        model.addAttribute("success", "User with email" + user.getEmail() + " has been registered successfully");
        return "redirect:/";
    }

    @ModelAttribute("roles")
    public List<Role> initializeRoles() {
        return roleService.getAll();
    }

    private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

}
