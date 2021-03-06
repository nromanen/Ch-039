package com.hospitalsearch.controller;

import com.hospitalsearch.dto.UserRegisterDTO;
import com.hospitalsearch.entity.PasswordResetToken;
import com.hospitalsearch.entity.Role;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.entity.VerificationToken;
import com.hospitalsearch.exception.ResetPasswordException;
import com.hospitalsearch.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
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
import java.net.ConnectException;
import java.util.List;
import java.util.Locale;
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
    private MessageSource messageSource;

    private static String emailTemplate = "emailTemplate.vm";

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
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            persistentTokenBasedRememberMeServices.logout(request, response, auth);
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        try {
            request.logout();
        } catch (ServletException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String getRegistration(@ModelAttribute("userDto") UserRegisterDTO userDto, ModelMap model) {
        model.addAttribute("userRegisterDto", userDto);
        return "/registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute("userDto") UserRegisterDTO userDto,
                               BindingResult result, ModelMap model, Locale locale) {
        if (result.hasErrors()) {
            return "registration";
        }
        User user = userService.register(userDto);
        String token = getRandomToken();
        verificationTokenService.createToken(token, user);
        try {
            String confirmationMessage = mailService.createRegisterMessage(user, token, locale);
            mailService.sendMessage(user, messageSource.getMessage("mail.message.registration.confirm", null, locale), confirmationMessage, emailTemplate);
        } catch (MailException | ConnectException e) {
            model.addAttribute("emailError", userDto.getEmail());
            verificationTokenService.deleteTokenByUser(user);
            userService.changeStatus(user.getId());
            return "/user/endRegistration";
        }
        model.addAttribute("emailSuccess", userDto.getEmail());
        return "/user/endRegistration";
    }

    @RequestMapping(value = "/confirmRegistration", method = RequestMethod.GET)
    public String confirmRegistration(@RequestParam("token") String token, ModelMap model) {
        VerificationToken verificationToken = verificationTokenService.getByToken(token);
        if (verificationToken == null) {
            model.addAttribute("invalidToken", "invalidToken");
            return "/user/confirmRegistration";
        }
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
        try {
            userService.resetPassword(userDto.getEmail(), userDto.getPassword());
            passwordResetTokenService.deleteTokenByUser(userService.getByEmail(userDto.getEmail()));
            model.addAttribute("successReset", "successReset");
        } catch (ResetPasswordException e) {
            model.addAttribute("errorReset", "errorReset");
            e.printStackTrace();
        }
        model.addAttribute("userDto", null);
        return "/user/confirmResetPassword";
    }

    @ResponseBody
    @RequestMapping(value = "resetPassword", method = RequestMethod.GET)
    public String resetPassword(@RequestParam("email") String email) throws ConnectException {
        User user = userService.getByEmail(email);
        Locale locale = LocaleContextHolder.getLocale();
        if (user == null) {
            return "invalidEmail";
        }
        if (verificationTokenService.getByUser(user) != null) {
            return "inactivated";
        }
        if (!user.getEnabled()) {
            return "banned";
        }
        if (passwordResetTokenService.getByUser(user) != null) {
            return "tokenCreated";
        }
        String token = getRandomToken();
        passwordResetTokenService.createToken(token, user);
        String resetPasswordMessage = mailService.createResetPasswordMessage(user, token, locale);
        mailService.sendMessage(user, messageSource.getMessage("mail.message.forgot.password", null, locale), resetPasswordMessage, emailTemplate);
        return "success";
    }

    //utility methods
    private String getRandomToken() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
