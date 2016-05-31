package com.hospitalsearch.controller;

import com.hospitalsearch.dto.UserSearchDTO;
import com.hospitalsearch.entity.Hospital;
import com.hospitalsearch.entity.Role;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.service.RoleService;
import com.hospitalsearch.service.UserService;
import com.hospitalsearch.util.PrincipalConverter;
import com.hospitalsearch.util.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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
    public String saveUser(@ModelAttribute ("user") User user, BindingResult result, ModelMap model) {
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
    public String adminAllUsers(ModelMap model,
                                @RequestParam(value = "status", required = false) String status) {
        List<User> users;
        switch (status) {
            case "false":
                users = userService.getAllDisabledUsers();
                model.addAttribute("status", "false");
                break;
            case "true":
                users = userService.getAllEnabledUsers();
                model.addAttribute("status", "true");
                break;
            default:
                users = userService.getAll();
                model.addAttribute("status", "All");
                break;
        }
        model.addAttribute("userSearch", new UserSearchDTO());
        model.addAttribute("users", users);
        return "admin/users";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = {"admin/users/changeStatus/{userId}"}, method = RequestMethod.GET)
    public void changeUserStatus(@PathVariable long userId) {
        userService.changeStatus(userId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "admin/users/delete/{userId}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable long userId,
                             @RequestParam(value = "status", required = false) String status) {
        userService.changeStatus(userId);
        return "redirect:/admin/users?status="+status;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "admin/users/edit", method = RequestMethod.GET)
    public ModelAndView editUser(@RequestParam("id") Long userId,
                                 @RequestParam ("status") String status) {
        ModelAndView modelAndView = new ModelAndView("admin/editUser");
        modelAndView.addObject("editUser", userService.getById(userId));
        modelAndView.addObject("status", status);
        return modelAndView;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "admin/users/edit", method = RequestMethod.POST)
    public String editUser(@ModelAttribute("editUser") User editUser, BindingResult result, ModelMap model,
                           @RequestParam("email") String email,
                           @RequestParam ("status") String status) {
        if (result.hasErrors()) {
            model.addAttribute("messageError", "Error updating, please try again!");
            return "redirect:/admin/users?status="+status;
        }
        try {
            User user = userService.getByEmail(email);
            user.setUserRoles(editUser.getUserRoles());
            user.setEnabled(editUser.getEnabled());
            userService.update(user);
        } catch (Exception e) {
            model.addAttribute("messageError", "Error updating, please try again!");
            return "redirect:/admin/users?status="+status;
        }
        model.addAttribute("messageSuccess", "User " + editUser.getEmail() + " successfully updated!");

        return "redirect:/admin/users?status="+status;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "admin/users/view/{id}", method = RequestMethod.GET)
    public @ResponseBody User viewUser(@PathVariable("id") String id) {
        return userService.getById(Long.parseLong(id));
    }

    @ModelAttribute("roles")
    public List<Role> initializeRoles() {
        return roleService.getAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="admin/users/search",method=RequestMethod.POST)
    public String renderFilteredHospitalsByPage(
            @Valid @ModelAttribute("userSearch") UserSearchDTO dto,
            BindingResult results, @RequestParam("status") String status,
            ModelMap model) throws Exception{
        List<User> users = userService.searchUser(dto);
        model.addAttribute("users", users);
        model.addAttribute("status", status);
        return "/admin/users";
    }
}

/*
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.writeValue(new FileOutputStream("/home/andrew/result.json"),userService.getById(Long.parseLong(id)));*/

