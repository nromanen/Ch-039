package com.hospitalsearch.controller;

import com.hospitalsearch.entity.Role;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.service.RoleService;
import com.hospitalsearch.service.UserService;
import com.hospitalsearch.util.PrincipalConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Andrew Jasinskiy on 10.05.16
 */
/*@RequestMapping(value = "/admin")*/
@Controller
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    //todo refactor after pulling this method into package admin url = admin/users/add
    @RequestMapping(value = "/newUser", method = RequestMethod.GET)
    public String newRegistration(ModelMap model) {
        model.addAttribute("user", new User());
        return "newuser";
    }

    //todo refactor after pulling this method into package admin url = admin/users/add
    @RequestMapping(value = "/newUser", method = RequestMethod.POST)
    public String saveUser(@Valid User user, BindingResult result, ModelMap model) {
        try {
            userService.save(user);
        } catch (Exception e) {
            model.addAttribute("registrationError", "User with email " + user.getEmail() + " is already exist!");
            return "/user/endRegistration";
        }
        model.addAttribute("success", "User with email " + user.getEmail() + " has been registered successfully");
        return "/user/endRegistration";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
    public String adminDashboard(ModelMap model) {
        List<User> users = userService.getAll();
        model.addAttribute("user", PrincipalConverter.getPrincipal());
        model.addAttribute("users", users);
        return "admin/users";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = {"admin/users/{userId}/changeStatus"}, method = RequestMethod.GET)
    public void changeUserStatus(@PathVariable long userId) {
        userService.changeStatus(userId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = {"admin/users/{userId}/delete"}, method = RequestMethod.GET)
    public String deleteUser(@PathVariable long userId) {
        userService.delete(userId);
        return "redirect:/admin/users";
    }


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "admin/users/edit", method = RequestMethod.GET)
    public String editUser(@RequestParam("id") Long userId, ModelMap model) {
        User editUser = userService.getById(userId);
        model.addAttribute("editUser", editUser);
        return "admin/editUser";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "admin/users/edit", method = RequestMethod.POST)
    public String editUser(@Valid User editUser, BindingResult result, ModelMap model) {
        System.out.println(editUser);
        System.out.println("************");
        System.out.println(editUser.getUserDetails());
        System.out.println(result);

        if (result.hasErrors()) {
            System.out.println("There are errors");
            model.addAttribute("messageError", "Error updating, please try again!");
            return "redirect:/admin/users";
        }
        try {

            userService.update(editUser);
            System.out.println("*************");
            System.out.println(editUser);
        } catch (Exception e) {
            model.addAttribute("messageError", "Error updating, please try again!");
            return "redirect:/admin/users";
        }
        model.addAttribute("messageSuccess", "User " + editUser.getEmail() + " successfully updated!");
        return "redirect:/admin/users";
    }

    @ModelAttribute("roles")
    public List<Role> initializeRoles() {
        return roleService.getAll();
    }

    @RequestMapping(value = {"/loginPopup"}, method = RequestMethod.POST)
    public String loginPopUp(BindingResult result, RedirectAttributes redirectAttributes) {
        return "/login";
    }
    /*"redirect:/admin/users";*/
/*
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.writeValue(new FileOutputStream("/home/andrew/result.json"),userService.getById(Long.parseLong(id)));*/
}
