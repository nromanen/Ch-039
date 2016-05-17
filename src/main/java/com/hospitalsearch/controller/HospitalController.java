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


/**
 * Created by deplague on 5/11/16.
 */
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

	@RequestMapping("/hos")
	public String renderHospitals2(Map<String,Object> model){
		save();

		return "layout";
	}

	@RequestMapping("/hospitals")
	public String renderHospitals(Map<String,Object> model){

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

	public void save(){

		String p = "1111";
		
		Hospital hospital = new Hospital();
		hospital.setId(1L);
		hospital.setName("Fastivska");
		hospital.setAddress("Chernivci");
		hospital.setLatitude(12.3);
		hospital.setLongitude(12.4);
		hospital.setImagePath("item1.jpg");
		hospital.setDescription("Some hosptials with some stuff");
		service.save(hospital);

		hospital = service.getById(1L);
		Department department = new Department();
		department.setId(1L);
		department.setHospital(hospital);
		department.setName("Heart");
		department.setImagePath("service1.png");


		Department department2 = new Department();
		department2.setHospital(hospital);
		department2.setName("Brain");
		department2.setImagePath("service2.png");
		department2.setId(2L);

		departmentService.save(department2);
		departmentService.save(department);


		User user = new User();
		user.setId(1L);
		user.setEmail("ghouse@pplaceboro.com");
		user.setFirstName("Gregory");
		user.setLastName("House");
		user.setImagePath("member2.jpg");
		user.setPassword(p);
		userService.save(user);


		DoctorInfo info = new DoctorInfo();
		info.setUserDetails((UserDetail)user);
		info.setSpecialization("Diagnostician");
		doctorInfoService.save(info);

		User user2 = new User();
		user2.setId(2L);
		user2.setEmail("lcuddy@pplaceboro.com");
		user2.setFirstName("Lisa");
		user2.setLastName("Cuddy");
		user2.setImagePath("member1.jpg");
		user.setPassword(p);
		userService.save(user2);

		DoctorInfo info2 = new DoctorInfo();
		info2.setUserDetails((UserDetail)user2);
		info2.setSpecialization("Endocrinologist");
		doctorInfoService.save(info2);

		User user3 = new User();
		user3.setId(3L);
		user3.setEmail("eforeman@pplaceboro.com");
		user3.setFirstName("Eric");
		user3.setLastName("Foreman");
		user3.setImagePath("member3.jpg");
		user.setPassword(p);
		userService.save(user3);

		DoctorInfo info3 = new DoctorInfo();
		info3.setUserDetails((UserDetail)user3);
		info3.setSpecialization("Neurologist");
		doctorInfoService.save(info3);
		 

		User user4 = new User();
		user4.setId(4L);
		user4.setEmail("jwilson@pplaceboro.com");
		user4.setFirstName("James");
		user4.setLastName("Willson");
		user4.setImagePath("member4.jpg");
		user.setPassword(p);
		userService.save(user4);

		DoctorInfo info4 = new DoctorInfo();
		info4.setUserDetails((UserDetail)user4);
		info4.setSpecialization("Onkologist");
		doctorInfoService.save(info4);

		User user5 = new User();
		user5.setId(5L);
		user5.setEmail("rheadley@pplaceboro.com");
		user5.setFirstName("Rhemy");
		user5.setLastName("Hadley");
		user5.setImagePath("member5.jpg");
		user.setPassword(p);
		userService.save(user5);

		DoctorInfo info5 = new DoctorInfo();
		info5.setUserDetails((UserDetail)user5);
		info5.setSpecialization("Diagnostician");
		doctorInfoService.save(info5);

		department.setDoctors(Arrays.asList(info,info2,info3,info4,info5));
		this.departmentService.update(department);
	}
}