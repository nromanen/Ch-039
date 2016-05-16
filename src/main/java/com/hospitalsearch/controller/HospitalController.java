package com.hospitalsearch.controller;

import com.hospitalsearch.entity.*;
import com.hospitalsearch.service.DepartmentService;
import com.hospitalsearch.service.DoctorInfoService;
import com.hospitalsearch.service.HospitalService;
import com.hospitalsearch.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;


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
    	model.put("hospital",lst.get(0).getHospital());
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
        Hospital hospital = new Hospital();
        hospital.setName("Fastivska");

        hospital.setAddress("Chernivci");
        hospital.setLatitude(12.3);
        hospital.setLatitude(12.4);
        hospital.setImagePath("item1.jpg");
        hospital.setDescription("Some hosptials with some stuff");
        Department department = new Department();
        department.setHospital(hospital);
        department.setName("Heart");
        department.setImagePath("service1.png");
        
        
        User user = new User();
        user.setEmail("pasha");
        user.setFirstName("Pavel");
        user.setLastName("Kuz");
        user.setImagePath("member2.jpg");
        
        DoctorInfo info = new DoctorInfo();
        info.setUserDetails((UserDetail)user);
        info.setSpecialization("Heart");
        
        User user2 = new User();
        user2.setEmail("sasha");
        user2.setFirstName("Lol");
        user2.setLastName("Kuz");
        user2.setImagePath("member1.jpg");
        
        DoctorInfo info2 = new DoctorInfo();
        info2.setUserDetails((UserDetail)user2);
        info2.setSpecialization("Brain");
        
        User user3 = new User();
        user3.setEmail("qwe");
        user3.setFirstName("Kolia");
        user3.setLastName("Kuz");
        user3.setImagePath("member3.jpg");
        
        DoctorInfo info3 = new DoctorInfo();
        info3.setUserDetails((UserDetail)user3);
        info3.setSpecialization("Knee");
        
        User user4 = new User();
        user4.setEmail("sasha");
        user4.setFirstName("Vasia");
        user4.setLastName("Pupkin");
        user4.setImagePath("member4.jpg");
        
        DoctorInfo info4 = new DoctorInfo();
        info4.setUserDetails((UserDetail)user4);
        info4.setSpecialization("Brain");
        
        User user5 = new User();
        user5.setEmail("sasha");
        user5.setFirstName("Lol");
        user5.setLastName("Kuz");
        user5.setImagePath("member5.jpg");
        
        DoctorInfo info5 = new DoctorInfo();
        info5.setUserDetails((UserDetail)user5);
        info5.setSpecialization("Brain");
        
        department.setDoctors(Arrays.asList(info,info2,info3,info4,info5));
        this.departmentService.save(department);
        
    }
}
