package com.hospitalsearch.controller;

import com.hospitalsearch.dto.UserAdminDTO;
import com.hospitalsearch.entity.Role;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.service.RoleService;
import com.hospitalsearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.beans.support.SortDefinition;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
/**
 * @author Andrew Jasinskiy on 10.05.16
 */
@Controller
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    private Integer usersPerPage = 10;
    private Integer currentPage = 1;

    @ModelAttribute("roles")
    public List<Role> initializeRoles() {
        return roleService.getAll();
    }

    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/admin/users/changeStatus/{userId}", method = RequestMethod.GET)
    public void changeUserStatus(@PathVariable long userId) {
        userService.changeStatus(userId);
    }

    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/admin/users/view/{id}")
    public User viewUser(@PathVariable("id") String id) {
        return userService.getById(Long.parseLong(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "admin/users/delete/{userId}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable long userId,
                             @RequestParam(value = "status", required = false) String status) {
        userService.changeStatus(userId);
        return "redirect:/admin/users?status=" + status;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "admin/users/edit", method = RequestMethod.GET)
    public ModelAndView editUser(@RequestParam("id") Long userId,
                                 @RequestParam("status") String status) {
        ModelAndView modelAndView = new ModelAndView("admin/editUser");
        modelAndView.addObject("editUser", userService.getById(userId));
        modelAndView.addObject("status", status);
        return modelAndView;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "admin/users/edit", method = RequestMethod.POST)
    public String editUser(@ModelAttribute("editUser") User editUser, BindingResult result, ModelMap model,
                           @RequestParam("email") String email,
                           @RequestParam("status") String status,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("messageError", "Error updating, please try again!");
            return "redirect:/admin/users?status=" + status;
        }
        try {
            User user = userService.getByEmail(email);
            user.setUserRoles(editUser.getUserRoles());
            user.setEnabled(editUser.getEnabled());
            userService.updateUser(user);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("messageError", "Error updating, please try again!");
            return "redirect:/admin/users?status=" + status;
        }
        redirectAttributes.addFlashAttribute("messageSuccess", "User " + editUser.getEmail() + " successfully updated!");
        return "redirect:/admin/users?status=" + status;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "admin/users", method = RequestMethod.GET)
    public String allUsers(ModelMap model, @RequestParam(value = "status", required = false) String status,
                           @ModelAttribute UserAdminDTO dto,
                           @RequestParam(value = "page", defaultValue = "1") Integer page,
                           @RequestParam(value = "sort", defaultValue = "email") String sort,
                           @RequestParam(value = "asc", defaultValue = "true") Boolean asc){
        dto.setCurrentPage(page);
        dto.setAsc(asc);
        dto.setSort(sort);
        dto.setPageSize(usersPerPage);
        dto.setStatus(status);
        List users = userService.getUsers(dto);
        if(dto.getTotalPage()>1)model.addAttribute("pagination", "pagination");
        model.addAttribute("userAdminDTO", dto);
        model.addAttribute("pageSize", dto.getPageSize());
        model.addAttribute("users", users);
        return "admin/users";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "admin/users/search", method = RequestMethod.GET)
    public String searchUser(@RequestParam("status") String status,
                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                             @ModelAttribute("userAdminDTO") UserAdminDTO dto,
                             ModelMap model) throws Exception {

       /* dto.setPageSize(usersPerPage);*/
        List users = userService.searchUser(dto);
        if(dto.getTotalPage()>1) model.addAttribute("pagination", "pagination");
        model.addAttribute("search", "search");
        model.addAttribute("userAdminDTO", dto);
        model.addAttribute("status", status);
        model.addAttribute("users", users);
        return "admin/users";
    }

  /*  @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "admin/users/setItemsPerPage/{value}", method = RequestMethod.GET)
    public String setItemsPerPage(@PathVariable int value) {
        usersPerPage = value;
        currentPage = 1;
        return "done";
    }*/
}
