package com.hospitalsearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hospitalsearch.entity.User;
import com.hospitalsearch.entity.UserDetail;
import com.hospitalsearch.service.FeedbackService;
import com.hospitalsearch.service.UserDetailService;
import com.hospitalsearch.service.UserService;
import com.hospitalsearch.util.FeedbackDTO;

@Controller
public class FeedbackController {

    @SuppressWarnings("unused")
	@Autowired
    private UserService userService;

    @Autowired
    private UserDetailService detailService;

    
    @Autowired
    private FeedbackService feedbackService;
    @RequestMapping(value="/doctor/feedback",method = RequestMethod.POST)
    @ResponseBody
    public String profile(@RequestBody FeedbackDTO dto){
        
    	User producer = feedbackService.getByUserEmail(dto.getUserEmail());
    	User consumer = userService.getById(dto.getDoctorId());
    		
    	feedbackService.save(dto.buildFeedback(consumer, producer));
    	return "true";
    }
}
