package com.hospitalsearch.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hospitalsearch.entity.Feedback;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.service.FeedbackService;
import com.hospitalsearch.service.UserService;
import com.hospitalsearch.util.FeedbackDTO;
import com.hospitalsearch.util.Page;
import com.hospitalsearch.util.PageConfigDTO;

@Controller
public class FeedbackController {
	@Autowired
    private UserService userService;
    
    @RequestMapping(value="/feed")
    public String feedbackWall(){
    	return "feedback_wall";
    }
    
    @Autowired
    private FeedbackService feedbackService;
    @RequestMapping(value="/doctor/feedback",method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('PATIENT')")
    @ResponseBody
    public String profile(@RequestBody FeedbackDTO dto){
        
    	User producer = feedbackService.getByUserEmail(dto.getUserEmail());
    	User consumer = userService.getById(dto.getDoctorId());
    		
    	feedbackService.save(dto.buildFeedback(consumer, producer));
    	return "true";
    }
    
    @RequestMapping(value="/doctor/feedback/check",method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('PATIENT')")
    @ResponseBody
    public String checkForRepeat(@RequestParam String email){
        if(!feedbackService.getFeedbacksByUserEmail(email).isEmpty()){
        	return "false";
        }
    	return "true";
    }
    
    
    
    @RequestMapping("/admin/feedbacks")
    @PreAuthorize("hasRole('ADMIN')")
    public String feedbackList(Map<String,Object> model){
    	this.initializeModel(model, 1);
    	return "feedback-admin";
    }
    @RequestMapping("/admin/feedbacks/{page}")
    @PreAuthorize("hasRole('ADMIN')")
    public String feedbackPage(Map<String,Object> model,@PathVariable Integer page){
    	this.initializeModel(model, page);
    	return "feedback-admin";
    }
    

    @RequestMapping("/admin/feedbacks/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String feedbackDelete(Map<String, Object> model,@PathVariable Long id){
    	feedbackService.delete(feedbackService.getById(id));
    	return "redirect:/admin/feedbacks";
    }
    @RequestMapping("/admin/feedbacks/filter")
    @PreAuthorize("hasRole('ADMIN')")
    public String feedbackFilter(Map<String, Object> model,@RequestParam String query, @RequestParam String criteria) throws InterruptedException{
    	switch (criteria) {
		case "message":
			model.put("feedbacks", feedbackService.filterByMessage(query));
			break;
		default:
			model.put("feedbacks", feedbackService.filterByEmail(query, criteria));
			break;
		}
    	model.put("pagination", false);
    	return "feedback-admin";
    }

    public void initializeModel(Map<String,Object> model,Integer page){
    	Page<Feedback> pageableContent= feedbackService.getFeedbacksByPage(page);
    	model.put("pagedList", pageableContent.getPageItems());
    	model.put("pagination", pageableContent.isPaginated());
        model.put("pageCount", pageableContent.getPageCount());
        model.put("pageSize", pageableContent.getPageSize());
        model.put("currentPage", page);
        model.put("feedbacks",pageableContent.getPageItems());
    }
    
}
