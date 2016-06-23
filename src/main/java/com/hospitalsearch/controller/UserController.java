package com.hospitalsearch.controller;

import com.hospitalsearch.dto.UserRegisterDTO;
import com.hospitalsearch.entity.PasswordResetToken;
import com.hospitalsearch.entity.Role;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.entity.VerificationToken;
import com.hospitalsearch.service.*;
import com.hospitalsearch.service.impl.ScheduledTokensService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * @author Andrew Jasinskiy on 16.05.16
 */

@Controller
public class UserController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    MailService mailService;

    @Autowired
    VerificationTokenService verificationTokenService;

    @Autowired
    PasswordResetTokenService passwordResetTokenService;

    @Autowired
    PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;

    @Autowired
    ScheduledTokensService scheduledTokensService; //todo delete after testing

    @ModelAttribute("roles")
    public List<Role> initializeRoles() {
        return roleService.getAll();
    }

    @RequestMapping(value = "/login")
    public String loginPage(ModelMap model) {
        model.addAttribute("email", request.getParameter("email"));
        model.addAttribute("error", request.getParameter("error"));
        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            persistentTokenBasedRememberMeServices.logout(request, response, auth);
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        request.logout();
        return "redirect:/";
    }

    @RequestMapping(value = "/newUser", method = RequestMethod.GET)
    public String newRegistration(ModelMap model) {
        model.addAttribute("user", new User());

        scheduledTokensService.deleteVerificationToken();

        return "newuser";
    }

    @RequestMapping(value = "/newUser", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute("user") User user, BindingResult result, ModelMap model) {
        try {
            userService.save(user);
        } catch (Exception e) {
            model.addAttribute("registrationError", "User with email " + user.getEmail() + " is already exist!");
            return "/user/endRegistration";
        }
        model.addAttribute("success", "User with email " + user.getEmail() + " has been registered successfully");
        return "/user/endRegistration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String getRegistration(@ModelAttribute("userDto") UserRegisterDTO userDto,
                                  BindingResult result, ModelMap model) {
        model.addAttribute("userRegisterDto", userDto);
        return "/registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute("userDto") UserRegisterDTO userDto,
                               BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return "registration";
        }
        User user = userService.register(userDto);
        String token = UUID.randomUUID().toString();
        String confirmationMessage = mailService.createRegisterMessage(user, token);
        verificationTokenService.createToken(token, user);
        mailService.sendMessage(user, "Registration confirmation", confirmationMessage);
        model.addAttribute("emailSuccess", userDto.getEmail());
        return "/user/endRegistration";
    }

    @RequestMapping(value = "/confirmRegistration", method = RequestMethod.GET)
    public String confirmRegistration(@RequestParam("token") String token, ModelMap model) {
        VerificationToken verificationToken = verificationTokenService.getByToken(token);
        User user = verificationToken.getUser();
        user.setEnabled(true);
        userService.update(user);
        verificationTokenService.delete(verificationToken);
        model.addAttribute("confirmEmail", user.getEmail());
        return "/user/confirmRegistration";
    }

    @RequestMapping(value = "/confirmResetPassword", method = RequestMethod.GET)
    public String confirmResetPassword(@RequestParam("token") String token, ModelMap model) {
        PasswordResetToken passwordResetToken = passwordResetTokenService.getByToken(token);
        if (passwordResetToken == null) {
            model.addAttribute("invalidToken", "invalidToken");
            return "/user/confirmResetPassword";
        }
        String email = passwordResetToken.getUser().getEmail();
        model.addAttribute("userDto", new UserRegisterDTO(email));
        return "/user/confirmResetPassword";
    }

    @RequestMapping(value = "/confirmResetPassword", method = RequestMethod.POST)
    public String confirmResetPass(@Valid @ModelAttribute("userDto") UserRegisterDTO userDto,
                                   BindingResult result, ModelMap model) {
        if (result.hasFieldErrors("password") || result.hasFieldErrors("confirmPassword")) {
            return "user/confirmResetPassword";
        }
        boolean reset = userService.resetPassword(userDto.getEmail(), userDto.getPassword());
        if (reset) {
            passwordResetTokenService.deleteTokenByUser(userService.getByEmail(userDto.getEmail()));
            model.addAttribute("successReset", "successReset");
        } else {
            model.addAttribute("errorReset", "errorReset");
        }
        model.addAttribute("userDto", null);
        return "/user/confirmResetPassword";
    }

    @ResponseBody
    @RequestMapping(value = "/**/resetPassword", method = RequestMethod.GET)
    public String resetPassword(@RequestParam("email") String email) {
        User user = userService.getByEmail(email);
        if (user == null) {
            return "invalidEmail";
        }
        if (verificationTokenService.getByUser(user) != null) {
            return "inactivated";
        }
        if (!user.getEnabled()) {
            return "banned";
        }
        if(passwordResetTokenService.getByUser(user)!=null){
            return "tokenCreated";
        }

        String token = UUID.randomUUID().toString();
        String resetPasswordMessage = mailService.createResetPasswordMessage(user, token);
        passwordResetTokenService.createToken(token, user);
        mailService.sendMessage(user, "Forgotten password", resetPasswordMessage);
        return "success";
    }
}
