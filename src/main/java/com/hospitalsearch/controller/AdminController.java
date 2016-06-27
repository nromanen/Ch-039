package com.hospitalsearch.controller;

import com.hospitalsearch.dto.AdminTokenConfigDTO;
import com.hospitalsearch.dto.UserFilterDTO;
import com.hospitalsearch.dto.UserRegisterDTO;
import com.hospitalsearch.entity.Role;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.service.MailService;
import com.hospitalsearch.service.RoleService;
import com.hospitalsearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.net.ConnectException;
import java.util.List;

import static com.hospitalsearch.config.security.SecurityConfiguration.REMEMBER_ME_TOKEN_EXPIRATION;
import static com.hospitalsearch.entity.PasswordResetToken.RESET_PASSWORD_TOKEN_EXPIRATION;
import static com.hospitalsearch.entity.VerificationToken.VERIFICATION_TOKEN_EXPIRATION;

/**
 * @author Andrew Jasinskiy on 10.05.16
 */
@Controller
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    MailService mailService;

    private Integer usersPerPage = 10;

    private static String emailTemplate = "emailTemplate.vm";

    @ModelAttribute("roles")
    public List<Role> initializeRoles() {
        return roleService.getAll();
    }


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/**/changeStatus/{userId}", method = RequestMethod.GET)
    public void changeUserStatus(@PathVariable long userId) throws ConnectException {
        userService.changeStatus(userId);
        sendBannedMessageToUserById(userId);
    }

    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/**/viewUser/{id}", method = RequestMethod.POST)
    public User viewUser(@PathVariable("id") String id) {
        return userService.getById(Long.parseLong(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "admin/users/delete/{userId}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable long userId,
                             @RequestParam(value = "status", required = false) String status) throws ConnectException {
        userService.changeStatus(userId);
        sendBannedMessageToUserById(userId);
        return "redirect:/admin/users?status=" + status;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/**/admin/users/edit", method = RequestMethod.GET)
    public ModelAndView editUser(@RequestParam("id") Long userId,
                                 @RequestParam("status") String status) {
        ModelAndView modelAndView = new ModelAndView("admin/editUser");
        modelAndView.addObject("editUser", userService.getById(userId));
        modelAndView.addObject("status", status);
        return modelAndView;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/**/admin/users/edit", method = RequestMethod.POST)
    public String editUser(@ModelAttribute("editUser") User editUser, BindingResult result, ModelMap model,
                           @RequestParam("status") String status,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("messageError", "messageError");
            return "redirect:/admin/users?status=" + status;
        }
        try {
            User user = userService.getByEmail(editUser.getEmail());
            user.setUserRoles(editUser.getUserRoles());
            user.setEnabled(editUser.getEnabled());
            userService.update(user);
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("messageError", "messageError");
            return "redirect:/admin/users?status=" + status;
        }
        redirectAttributes.addFlashAttribute("messageSuccess", editUser.getEmail());
        return "redirect:/admin/users?status=" + status;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "admin/users", method = RequestMethod.GET)
    public String allUsers(ModelMap model, @RequestParam(value = "status", required = false) String status,
                           @ModelAttribute UserFilterDTO dto,
                           @RequestParam(value = "page", defaultValue = "1") Integer page,
                           @RequestParam(value = "sort", defaultValue = "email") String sort,
                           @RequestParam(value = "asc", defaultValue = "true") Boolean asc) {

        dto.setCurrentPage(page);
        dto.setAsc(asc);
        dto.setSort(sort);
        dto.setPageSize(usersPerPage);
        dto.setStatus(status);
        List users = userService.getUsers(dto);
        if (dto.getTotalPage() > 1) model.addAttribute("pagination", "pagination");
        model.addAttribute("userFilterDTO", dto);
        model.addAttribute("pageSize", dto.getPageSize());
        model.addAttribute("users", users);
        return "admin/users";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "admin/users/search", method = RequestMethod.GET)
    public String searchUser(@RequestParam("status") String status,
                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                             @ModelAttribute("userFilterDTO") UserFilterDTO dto,
                             ModelMap model) throws Exception {
        dto.setPageSize(usersPerPage);
        List users = userService.searchUser(dto);
        if (dto.getTotalPage() > 1) model.addAttribute("pagination", "pagination");
        model.addAttribute("search", "search");
        model.addAttribute("userFilterDTO", dto);
        model.addAttribute("status", status);
        model.addAttribute("users", users);
        return "admin/users";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "admin/configureToken", method = RequestMethod.GET)
    public String configureToken(ModelMap model) throws Exception {
        model.addAttribute("configDTO", new AdminTokenConfigDTO());
        return "admin/configureToken";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "admin/configureToken", method = RequestMethod.POST)
    public String configureTokens(ModelMap model,
                                  @Valid @ModelAttribute("configDTO") AdminTokenConfigDTO configDTO,
                                  BindingResult result) throws Exception {
        if (result.hasErrors()) {
            return "admin/configureToken";
        }
        RESET_PASSWORD_TOKEN_EXPIRATION = configDTO.getResetPasswordToken();
        VERIFICATION_TOKEN_EXPIRATION = configDTO.getVerificationToken();
        REMEMBER_ME_TOKEN_EXPIRATION = configDTO.getRememberMeToken();
        model.addAttribute("configDTO", configDTO);
        return "admin/configureToken";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "admin/newUser", method = RequestMethod.GET)
    public String newRegistration(@ModelAttribute("userDto") UserRegisterDTO userDto, ModelMap model) {
        model.addAttribute("userRegisterDto", userDto);
        return "admin/newUser";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "admin/newUser", method = RequestMethod.POST)
    public String saveUser(@Valid @ModelAttribute("userDto") UserRegisterDTO userDto, BindingResult result,
                           ModelMap model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/newUser";
        }
        try {
            userService.register(userDto);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("messageError", "Error creating new user, please try again!");
            return "redirect:/admin/users?status=true";
        }
        redirectAttributes.addFlashAttribute("messageSuccess", "User with email " + userDto.getEmail() + " successfully has been registered.");
        return "redirect:/admin/users?status=true";
    }

    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "admin/users/setItemsPerPage/{value}", method = RequestMethod.GET)
    public String setItemsPerPage(@PathVariable int value) {
        usersPerPage = value;
        return "done";
    }

    //utility methods
    private void sendBannedMessageToUserById(Long id) throws ConnectException {
        User user = userService.getById(id);
        if (!user.getEnabled()) {
            String bannedMessage = mailService.createBannedMessage(user);
            mailService.sendMessage(user, "Banned confirmation", bannedMessage, emailTemplate);
        }
    }
}
