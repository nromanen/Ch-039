package com.hospitalsearch.controller;

import com.hospitalsearch.entity.CardItem;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.service.CardItemService;
import com.hospitalsearch.service.PatientCardService;
import com.hospitalsearch.service.UserService;
import com.hospitalsearch.util.PrincipalConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;


@Controller
public class CardController {

    private static final Integer ItemsPerPageCount = 3;

    @Autowired
    CardItemService cardItemService;

    @Autowired
    PatientCardService patientCardService;

    @Autowired
    UserService userService;

    private PagedListHolder pagedListHolder = new PagedListHolder<>();

    CardController(){
        pagedListHolder.setPageSize(ItemsPerPageCount);
        pagedListHolder.setPage(0);
    }

    @RequestMapping(value = {"/card"}, method = RequestMethod.GET)
    public String patientCard(ModelMap model) {
        User user = userService.getByEmail(PrincipalConverter.getPrincipal());
        model.addAttribute("cardItems", user.getUserDetails().getPatientCard().getCardItems());
        return "card/full";
    }

    @RequestMapping(value = {"/card/items"}, method = RequestMethod.GET)
    public String patientCard(@RequestParam("userId") String userId, ModelMap model) {
        User user = userService.getById(Long.parseLong(userId));
        pagedListHolder.setSource(cardItemService.getCardItemList(user));
        Boolean pagination = true;
        if (pagedListHolder.getPageCount()==1){
            pagination=false;
        }
        model.addAttribute("doctor",PrincipalConverter.getPrincipal());
        model.addAttribute("pagination",pagination);
        model.addAttribute("cardItems", pagedListHolder);
        model.addAttribute("userId", userId);
        model.addAttribute("name",user.getUserDetails().getFirstName()+" "+user.getUserDetails().getLastName());
        return "card/full";
    }

    @RequestMapping(value = {"/card/items/page"}, method = RequestMethod.GET)
    public String patientCardPage(@RequestParam("userId") String userId, @RequestParam ("page") String page, ModelMap model) {
        User user = userService.getById(Long.parseLong(userId));
        pagedListHolder.setPage(Integer.parseInt(page)-1);
        boolean b;
        model.addAttribute("doctor",PrincipalConverter.getPrincipal());
        model.addAttribute("pagination",true);
        model.addAttribute("cardItems", pagedListHolder);
        model.addAttribute("userId", userId);
        model.addAttribute("name",user.getUserDetails().getFirstName()+" "+user.getUserDetails().getLastName());
        return "card/full";
    }

    @RequestMapping(value = {"/new/record"}, method = RequestMethod.GET)
    public String newCardItem(@RequestParam("userId") String userId, ModelMap model) {
        model.addAttribute("cardItem", new CardItem());
        model.addAttribute("userId", userId);
        return "card/record";
    }

    @RequestMapping(value = {"/persist"}, method = RequestMethod.POST)
    public String addCardItem(@RequestParam("userId") String userId, @Valid CardItem cardItem, BindingResult bindingResult, ModelMap model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userId", userId);
            return "card/record";
        }
        String doctorEmail = PrincipalConverter.getPrincipal();
        if (!cardItemService.persist(cardItem, doctorEmail, Long.parseLong(userId))) {
            model.addAttribute("doctorError", "You cant edit this record");
            model.addAttribute("userId", userId);
            return "card/record";
        }

        model.addAttribute("userId", userId);
        return "redirect:card/items";
    }


    @RequestMapping(value = {"/edit"}, method = RequestMethod.GET)
    public String editCardItem(@RequestParam("id") String id, @RequestParam("userId") String userId, ModelMap model) {
        CardItem cardItem = cardItemService.getById(Long.parseLong(id));
        model.addAttribute("cardItem", cardItem);
        model.addAttribute("userId", userId);
        return "card/record";
    }

    @RequestMapping(value = {"/patients"}, method = RequestMethod.GET)
    public String patients(ModelMap model) {
        List<User> patients = userService.getByRole("PATIENT");
        Boolean pagination = true;
        pagedListHolder.setSource(patients);
        if (pagedListHolder.getPageCount()==1){
            pagination=false;
        }
        model.addAttribute("search",false);
        model.addAttribute("pagination",pagination);
        model.addAttribute("patients", pagedListHolder);
        return "card/patients";
    }

    @RequestMapping(value = {"/patients/page"}, method = RequestMethod.GET)
    public String patientsPage(@RequestParam ("page") String page, ModelMap model) {
        List<User> patients = userService.getByRole("PATIENT");
        Boolean pagination = true;
        pagedListHolder.setPage(Integer.parseInt(page)-1);
        pagedListHolder.setSource(patients);
        if (pagedListHolder.getPageCount()==1){
            pagination=false;
        }
        model.addAttribute("search",false);
        model.addAttribute("pagination",pagination);
        model.addAttribute("patients", pagedListHolder);
        return "card/patients";
    }


    @RequestMapping(value = {"/search/user"}, method = RequestMethod.POST)
    public String searchUser(@RequestParam("request") String request, ModelMap model) {
        List<User> patients = userService.searchByRole("PATIENT",request);
        Boolean pagination = true;
        pagedListHolder.setSource(patients);
        if (pagedListHolder.getPageCount()==1){
            pagination=false;
        }
        model.addAttribute("search",true);
        model.addAttribute("request",request);
        model.addAttribute("pagination",pagination);
        model.addAttribute("patients", pagedListHolder);
        return "card/patients";
    }

    @RequestMapping(value = {"/search/user/page"}, method = RequestMethod.GET)
    public String searchUserPage(@RequestParam("request") String request, @RequestParam ("page") String page, ModelMap model) {
        List<User> patients = userService.searchByRole("PATIENT",request);
        Boolean pagination = true;
        pagedListHolder.setPage(Integer.parseInt(page)-1);
        pagedListHolder.setSource(patients);
        if (pagedListHolder.getPageCount()==1){
            pagination=false;
        }
        model.addAttribute("search",true);
        model.addAttribute("request",request);
        model.addAttribute("pagination",pagination);
        model.addAttribute("patients", pagedListHolder);
        return "card/patients";
    }

}
