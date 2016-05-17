package com.hospitalsearch.controller;

import com.hospitalsearch.entity.Role;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.service.RoleService;
import com.hospitalsearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Created by andrew on 16.05.16.
 */

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            /*new SecurityContextLogoutHandler().logout(request, response, auth);*/
            persistentTokenBasedRememberMeServices.logout(request, response, auth);
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "redirect:/";
        /*return "redirect:/login?logout";*/
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public String saveRegistration(@Valid User user, BindingResult result, ModelMap model) {
        user.getUserRoles().add(roleService.getByType("PATIENT"));
        try{
            userService.save(user);
        }catch (Exception e){
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


    @RequestMapping(value = {"user/view/{id}"}, method = RequestMethod.GET)
    @ResponseBody
    public String viewUser(@PathVariable("id")String id, ModelMap model) {
        User user = userService.getById(Long.parseLong(id));
        String tmpUser = String.format ("%d,%s,%s,%s,%s,%s,%s,%s,%s",user.getId(),user.getEmail(),
                user.getGender(),user.getFirstName(),user.getLastName(),
                user.getBirthDate(),user.getAddress(),user.getPhone(),
                user.getPatientsDetails());
        return tmpUser;
    }


}
