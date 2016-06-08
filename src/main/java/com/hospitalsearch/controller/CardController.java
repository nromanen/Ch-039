package com.hospitalsearch.controller;

import com.hospitalsearch.entity.CardItem;
import com.hospitalsearch.entity.PatientCard;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.service.CardItemService;
import com.hospitalsearch.service.PatientCardService;
import com.hospitalsearch.service.UserService;
import com.hospitalsearch.util.PrincipalConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    private static final Integer itemsPerPage = 1;
    @Autowired
    CardItemService cardItemService;

    @Autowired
    PatientCardService patientCardService;

    @Autowired
    UserService userService;

    @PreAuthorize("hasRole('PATIENT')")
    @RequestMapping(value = {"/card"}, method = RequestMethod.GET)
    public String patientCard(@RequestParam(value = "page", defaultValue = "1") Integer page, ModelMap model) {
        User user = userService.getByEmail(PrincipalConverter.getPrincipal());
        List<CardItem> cardItems = cardItemService.getCardItemList(user, page, itemsPerPage);
        PatientCard patientCard = user.getUserDetails().getPatientCard();
        Long cardItemsCount = cardItemService.countOfItems(patientCard);
        Integer pageCount = userService.pageCount(cardItemsCount, itemsPerPage);
        Boolean pagination = false;
        if ( pageCount > 1){
            pagination = true;
        }
        model.addAttribute("page",page);
        model.addAttribute("pageCount",pageCount);
        model.addAttribute("pagination", pagination);
        model.addAttribute("cardItems", cardItems);
        model.addAttribute("name", user.getUserDetails().getFirstName() + " " + user.getUserDetails().getLastName());
        return "card/full";
    }


    @PreAuthorize("hasRole('DOCTOR')")
    @RequestMapping(value = {"/card/items"}, method = RequestMethod.GET)
    public String patientCard(@RequestParam("userId") String userId, @RequestParam(value = "page", defaultValue = "1") Integer page, ModelMap model) {
        User user = userService.getById(Long.parseLong(userId));
        List<CardItem> cardItems = cardItemService.getCardItemList(user, page, itemsPerPage);
        Long cardItemsCount = cardItemService.countOfItems(user.getUserDetails().getPatientCard());
        Boolean pagination = false;
        Integer pageCount = userService.pageCount(cardItemsCount, itemsPerPage);
        if ( pageCount > 1){
            pagination = true;
        }
        model.addAttribute("pageCount",pageCount);
        model.addAttribute("page", page);
        model.addAttribute("patients", "breadcrumbs");
        model.addAttribute("doctor", PrincipalConverter.getPrincipal());
        model.addAttribute("pagination", pagination);
        model.addAttribute("cardItems", cardItems);
        model.addAttribute("userId", userId);
        model.addAttribute("name", user.getUserDetails().getFirstName() + " " + user.getUserDetails().getLastName());
        return "card/full";
    }


    @PreAuthorize("hasRole('DOCTOR')")
    @RequestMapping(value = {"/new/record"}, method = RequestMethod.GET)
    public String newCardItem(@RequestParam("userId") Long userId, ModelMap model) {
        model.addAttribute("patients", "breadcrumbs");
        model.addAttribute("cardItem", new CardItem());
        model.addAttribute("userId", userId);
        return "card/record";
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @RequestMapping(value = {"/persist"}, method = RequestMethod.POST)
    public String addCardItem(@RequestParam("userId") Long userId, @Valid CardItem cardItem, BindingResult bindingResult, ModelMap model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userId", userId);
            return "card/record";
        }
        String doctorEmail = PrincipalConverter.getPrincipal();
        if (!cardItemService.persist(cardItem, doctorEmail, userId)) {
            model.addAttribute("doctorError", "You cant edit this record");
            model.addAttribute("userId", userId);
            return "card/record";
        }
        model.addAttribute("page",1);
        model.addAttribute("userId", userId);
        return "redirect:card/items";
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @RequestMapping(value = {"/edit"}, method = RequestMethod.GET)
    public String editCardItem(@RequestParam("id") String id, @RequestParam("userId") Long userId, ModelMap model) {
        CardItem cardItem = cardItemService.getById(Long.parseLong(id));
        model.addAttribute("patients", "breadcrumbs");
        model.addAttribute("cardItem", cardItem);
        model.addAttribute("userId", userId);
        return "card/record";
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @RequestMapping(value = {"/patients"}, method = RequestMethod.GET)
    public String patients(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "sortBy", defaultValue = "firstName") String sortBy,
                           @RequestParam(value = "order", defaultValue = "false") Boolean order, ModelMap model) {
        List<User> patients = userService.getByRole("PATIENT", page, itemsPerPage, sortBy, order);

        Long patientsCount = userService.countOfUsersByRole("PATIENT");
        Boolean pagination = false;
        Integer pageCount = userService.pageCount(patientsCount,itemsPerPage);
        if ( pageCount > 1){
            pagination = true;
        }
        model.addAttribute("order", order);
        model.addAttribute("sortBy",sortBy);
        model.addAttribute("page",page);
        model.addAttribute("pageSize", itemsPerPage);
        model.addAttribute("pageCount",pageCount);
        model.addAttribute("search", false);
        model.addAttribute("pagination", pagination);
        model.addAttribute("patients", patients);
        model.addAttribute("page",page);
        return "card/patients";
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @RequestMapping(value = {"/search/user"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String searchUser(@RequestParam("request") String request, @RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "sortBy", defaultValue = "firstName") String sortBy,
                             @RequestParam(value = "order", defaultValue = "false") Boolean order, ModelMap model) {
        List<User> patients = userService.searchByRole("PATIENT", request,page,itemsPerPage, sortBy, order);
        Long patientsCount = userService.countOfUsersByRole("PATIENT", request);
        Boolean pagination = false;
        Integer pageCount = userService.pageCount(patientsCount,itemsPerPage);
        if (pageCount > 1){
            pagination = true;
        }
        model.addAttribute("pageSize",itemsPerPage);
        model.addAttribute("order", order);
        model.addAttribute("sortBy",sortBy);
        model.addAttribute("pageCount",pageCount);
        model.addAttribute("search", true);
        model.addAttribute("request", request);
        model.addAttribute("pagination", pagination);
        model.addAttribute("patients", patients);
        model.addAttribute("page",page);
        return "card/patients";
    }



}
