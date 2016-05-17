package com.hospitalsearch.controller;

import com.hospitalsearch.entity.CardItem;
import com.hospitalsearch.entity.PatientCard;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.service.CardItemService;
import com.hospitalsearch.service.PatientCardService;
import com.hospitalsearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;


@Controller
public class CardController {

    @Autowired
    CardItemService cardItemService;

    @Autowired
    PatientCardService patientCardService;

    @Autowired
    UserService userService;

    @RequestMapping(value = { "/card" }, method = RequestMethod.GET)
    public String patientCard(ModelMap model){
        User user = userService.getByEmail(getPrincipal());
        model.addAttribute("cardItems", user.getPatientCard().getCardItems());
        return "card/full";
    }

    @RequestMapping(value = { "/cards" }, method = RequestMethod.GET)
    public String patientCard(@RequestParam("userId") String userId, ModelMap model){
        User user = userService.getById(Long.parseLong(userId));
        model.addAttribute("cardItems", user.getPatientCard().getCardItems());
        model.addAttribute("userId",userId);
        return "card/full";
    }

    @RequestMapping(value = { "/new/record" }, method = RequestMethod.GET)
    public String newCardItem(@RequestParam("userId") String userId,ModelMap model) {
        model.addAttribute("cardItem", new CardItem());
        model.addAttribute("userId", userId);
        return "card/new";
    }

    @RequestMapping(value = { "/persist" }, method = RequestMethod.POST)
    public String addCardItem(@RequestParam("userId") String userId, @Valid CardItem cardItem, BindingResult bindingResult, ModelMap model){
        if (bindingResult.hasErrors()){
            model.addAttribute("userId", userId);
            return "card/new";
        }
        User user = userService.getByEmail(getPrincipal());
        PatientCard patientCard = userService.getById(Long.parseLong(userId)).getPatientCard();
        cardItem.setDoctor(user);
        cardItem.setPatientCard(patientCard);
        cardItemService.persist(cardItem);
        model.addAttribute("userId", userId);
        return "redirect:cards";
    }


    @RequestMapping(value = { "/edit" }, method = RequestMethod.GET)
    public String editCardItem(@RequestParam("id") String id, @RequestParam("userId") String userId, ModelMap model){
        CardItem cardItem = cardItemService.getById(Long.parseLong(id));
        model.addAttribute("cardItem",cardItem);
        model.addAttribute("userId",userId);
        return "card/new";
    }

    @RequestMapping(value = { "/patients" }, method = RequestMethod.GET)
    public String patients(ModelMap model){
        model.addAttribute("patients",userService.getByRole(2));
        return "card/patients";
    }

    private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

}
