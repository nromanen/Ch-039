package com.hospitalsearch.controller.advice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.hospitalsearch.controller.HospitalController;
import com.hospitalsearch.entity.Hospital;
import com.hospitalsearch.service.HospitalService;
import com.hospitalsearch.util.HospitalFilterDTO;
import com.hospitalsearch.util.PageConfigDTO;

@ControllerAdvice(assignableTypes={HospitalController.class})
public class HospitalControllerAdvice {
	@Autowired(required = true)
	private HospitalService service;
	
	@ResponseStatus(code=HttpStatus.NOT_FOUND)
	@ExceptionHandler(value={FilterHospitalListEmptyException.class})
	public ModelAndView renderHospitalListException(Exception ex){
		ModelAndView view = new ModelAndView("error/hospitalList");
		return view;
	}
	
    
        
	@ModelAttribute(value = "hospitals")
	public List<Hospital> hospitalList(){
		return this.service.getAll();
	}
	 
	@ModelAttribute(value="filter")
	public HospitalFilterDTO hospitalFilterDTO(){return new HospitalFilterDTO();}
	
        @ModelAttribute
	public void hospitalFilterDTO(ModelMap model){
            model.addAttribute("globalSearch",new PageConfigDTO());
        }
	
        
	public static class FilterHospitalListEmptyException extends Exception{
		public FilterHospitalListEmptyException(String message) {
			super(message);
		}
	}
}
