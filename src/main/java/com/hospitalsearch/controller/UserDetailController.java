package com.hospitalsearch.controller;

import com.hospitalsearch.entity.User;
import com.hospitalsearch.entity.UserDetail;
import com.hospitalsearch.service.UserDetailService;
import com.hospitalsearch.service.UserService;
import com.hospitalsearch.util.PrincipalConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
public class UserDetailController {
    @Autowired
    UserDetailService userDetailService;

    @Autowired
    UserService userService;

    @RequestMapping(value = { "/user/detail" }, method = RequestMethod.GET)
    public String userDetail(ModelMap model){
        model.addAttribute("userDetail", new UserDetail());
        return "user/userdetail";
    }

    @ResponseBody
    @RequestMapping(value = { "/save/detail" }, method = RequestMethod.POST)
    public String saveUserDetail(@Valid UserDetail userDetail, ModelMap model){
        User user = userService.getByEmail(PrincipalConverter.getPrincipal());
        userDetail = userDetailService.add(userDetail);
        user.setUserDetails(userDetail);
        userService.update(user);
        return user.toString();
    }
}
