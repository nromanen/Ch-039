package com.hospitalsearch.controller;


import com.hospitalsearch.dto.UserRegisterDTO;
import com.hospitalsearch.service.UserDetailService;
import com.hospitalsearch.service.UserService;
import com.hospitalsearch.util.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/register")
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailService userDetailService;

    @RequestMapping(method = RequestMethod.POST)
    public String processRegistration(@Valid @ModelAttribute("dto") UserRegisterDTO dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "layout";
        }
        userService.register(dto);
        model.addAttribute("dto", null);
        return "redirect:/";
    }


    @RequestMapping(value = "/{email}", method = RequestMethod.GET)
    public String viewUser(@PathVariable("email") String email, Model model) {
        model.addAttribute("dto", userService.getDtoByEmail(email));
        return "registercontinue";
    }

    @RequestMapping(value = "/edit/{email}", method = RequestMethod.POST)
    public String updateUser(@PathVariable("email") String email,
                             @Valid @ModelAttribute UserDto dto,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "registercontinue";
        }
        userService.registerUpdate(dto, email);
        return "userinfo";

    }
}