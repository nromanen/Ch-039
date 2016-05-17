package com.hospitalsearch.controller;

import com.hospitalsearch.entity.Role;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.service.RoleService;
import com.hospitalsearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by andrew on 10.05.16.
 */

@Controller
@SessionAttributes("roles")
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        return "403";
    }

    @RequestMapping(value = "/newUser", method = RequestMethod.GET)
    public String newRegistration(ModelMap model) {
        User user = new User();
        model.addAttribute("user", user);
        return "newuser";
    }

    @RequestMapping(value = "/newUser", method = RequestMethod.POST)
    public String saveRegistration(@Valid User user, BindingResult result, ModelMap model) {
        try{
            userService.save(user);
        }catch (Exception e) {
            model.addAttribute("registrationError", "User with email " + user.getEmail() + " is already exist!");
            return "/user/endRegistration";
        }
        if (result.hasErrors()) {
            System.out.println("There are errors");
            model.addAttribute("registrationError", "Error registration, please try again!");
            return "/user/endRegistration";
        }
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
        model.addAttribute("success", "User with email " + user.getEmail() + " has been registered successfully");
        return "/user/endRegistration";
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/dashboard", method = RequestMethod.GET)
    public String adminDashboard(ModelMap model) {
        System.out.println("!!!!!!!!!!!!!!!!!!!");
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        List<User> users = userService.getAll();
        model.addAttribute("user", getPrincipal());
        model.addAttribute("users", users);
        return "admin/dashboard";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = { "/changeStatus/{userId}"}, method = RequestMethod.GET)
    public void changeUserStatus(@PathVariable long userId){
        User user = userService.getById(userId);
        userService.changeStatus(user);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"admin/delete/{userId}"}, method = RequestMethod.GET)
    public String deleteUser(@PathVariable long userId) {
        User user = userService.getById(userId);
        System.out.println(user);
        userService.delete(user);
        return "redirect:/admin/dashboard";
    }


    @RequestMapping(value = "/editUser", method = RequestMethod.GET)
    public String editUser(@RequestParam ("id") Long userId, ModelMap model) {

        User editUser = userService.getById(userId);
        model.addAttribute("editUser", editUser);
        return "admin/editUser";
    }


    @RequestMapping(value = "/editUser", method = RequestMethod.POST)
    public String updateUser(@Valid User editUser, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            System.out.println("There are errors");
            model.addAttribute("messageError", "Error updating, please try again!");
            return "redirect:/admin/dashboard";
        }
        try{
            userService.update(editUser);
        }catch (Exception e) {
            model.addAttribute("messageError", "Error updating, please try again!");
            return "redirect:/admin/dashboard";
        }
        model.addAttribute("messageSuccess", "User " + editUser.getEmail() + " successfully updated!");
        return "redirect:/admin/dashboard";
    }
}
