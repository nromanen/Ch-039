package com.hospitalsearch.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hospitalsearch.entity.Department;
import com.hospitalsearch.entity.DoctorInfo;
import com.hospitalsearch.entity.Hospital;
import com.hospitalsearch.entity.User;
import com.hospitalsearch.entity.UserDetail;
import com.hospitalsearch.service.DepartmentService;
import com.hospitalsearch.service.DoctorInfoService;
import com.hospitalsearch.service.HospitalService;
import com.hospitalsearch.service.UserService;



@Controller
public class HospitalController {

	@Autowired(required = true)
	private HospitalService service;

	@Autowired(required = true)
	private DepartmentService departmentService;

	@Autowired(required = true)
	private UserService userService;


	@Autowired(required = true)
	private DoctorInfoService doctorInfoService;

	@RequestMapping("/")
	public String renderIndex(Map<String,Object> model){
		return "layout";
	}

	@RequestMapping("/hospitals")
	public String renderHospitals(Map<String,Object> model){
		model.put("hospitals", service.getAll());
    	return "hospitals";
	}


	@RequestMapping("/hospital/{id}")
	public String renderDepartments(Map<String,Object> model,
			@PathVariable Long id
			){
		List<Department> lst = departmentService.findByHospitalId(id);

		model.put("departments",lst);
		model.put("hospital",service.getById(id));
		model.put("hid",id);

		return "departments";
	}


	@RequestMapping("/hospital/{hid}/department/{id}")
	public String renderDoctors(Map<String,Object> model,
			@PathVariable Long hid,
			@PathVariable Long id
	){
		Department d =departmentService.getById(id);
		model.put("doctors", doctorInfoService.findByDepartmentId(id));
		model.put("department", d);
		model.put("hospital", d.getHospital());

		model.put("hid",hid);
		model.put("id",id);

		return "doctors";
	}



	@ModelAttribute(value = "hospitals")
	public List<Hospital> hospitalList(){
		return this.service.getAll();
	}

}
