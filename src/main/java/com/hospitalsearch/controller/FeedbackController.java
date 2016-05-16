package com.hospitalsearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hospitalsearch.entity.Feedback;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.service.FeedbackService;
import com.hospitalsearch.service.PatientService;
import com.hospitalsearch.service.UserDetailService;
import com.hospitalsearch.service.UserService;
import com.hospitalsearch.util.FeedbackDTO;

@Controller
public class FeedbackController {
    @Autowired
    private PatientService service;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailService detailService;

    
    @Autowired
    private FeedbackService feedbackService;
    @RequestMapping(value="/doctor/feedback")
    @ResponseBody
    public String profile(
    		@RequestParam String userEmail,
 
    		@RequestParam Long doctorId,
    		@RequestParam String message){
        
    	User user = feedbackService.getByUserEmail(userEmail);
//    	Feedback f = feedbackService.getByProducer(user);
//    	if(f!= null && !f.getMessage().isEmpty()){
//    		return "false";
//    	}
  
    	FeedbackDTO dto = new FeedbackDTO(feedbackService.getByUserEmail(userEmail),doctorId,message);
 
    	feedbackService.save(dto.getFeedback(detailService));
    	return "true";
    }
}
