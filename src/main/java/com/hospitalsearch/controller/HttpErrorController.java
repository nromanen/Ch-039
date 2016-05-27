package com.hospitalsearch.controller;

import com.hospitalsearch.util.PrincipalConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * @author Andrew Jasinskiy on 25.05.16
 */
@Controller
public class HttpErrorController {

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("user", PrincipalConverter.getPrincipal());
        return "error/403";
    }
    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public String pageNotFound() {
        return "error/404";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String errorPage() {
        return "error/500";
    }
}
