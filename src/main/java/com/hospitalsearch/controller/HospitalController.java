package com.hospitalsearch.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.beans.support.SortDefinition;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hospitalsearch.controller.advice.HospitalControllerAdvice;
import com.hospitalsearch.entity.Department;
import com.hospitalsearch.entity.Hospital;
import com.hospitalsearch.entity.HospitalAddress;
import com.hospitalsearch.service.DepartmentService;
import com.hospitalsearch.service.DoctorInfoService;
import com.hospitalsearch.service.HospitalService;
import com.hospitalsearch.util.HospitalFilterDTO;



@Controller
public class HospitalController {
	private static final Integer ITEMS_PER_PAGE_COUNT = 15;

	@Autowired(required = true)
	private HospitalService service;

	@Autowired(required = true)
	private DepartmentService departmentService;

	@Autowired(required = true)
	private DoctorInfoService doctorInfoService;
	private PagedListHolder<Hospital> pagedListHolder = new PagedListHolder<>();

	private List<Hospital> currentHospitalList;

	public HospitalController() {
		pagedListHolder.setPageSize(ITEMS_PER_PAGE_COUNT);
		pagedListHolder.setSort(new SortDefinition() {

			@Override
			public boolean isIgnoreCase() {
				return true;
			}

			@Override
			public boolean isAscending() {
				return false;
			}

			@Override
			public String getProperty() {
				return "name";
			}
		});

	}

	@RequestMapping("/")
	public String renderIndex(Map<String,Object> model){
		return "layout";
	}

//	public void fillBase(){		
//		HospitalAddress [] address = new HospitalAddress[30];
//		for(int i=0;i< 30;i++){
//			address[i] = new HospitalAddress();
//			address[i].setCity("Chernivci "+i);
//			address[i].setCountry("Ukraine");
//			address[i].setStreet("Bogdana,"+i);
//
//		}
//
//		for(int i=0;i<30;i++){
//			Hospital h = new Hospital();
//			h.setAddress(address[i]);
//			h.setName("Fastovska"+i);
//			h.setLatitude(2d);
//			h.setLongitude(3d);
//			h.setImagePath("Hospital_1.jpg");
//			h.setDescription("Very cool");
//			service.save(h);
//		}
//	}

	@RequestMapping("/hospitals")
	public String renderHospitals(Map<String,Object> model,
			@RequestParam(value="q",required=false) String query) throws ParseException, InterruptedException{
		
		pagedListHolder.setPage(0);
		if(query!= null && !query.isEmpty()){
			pagedListHolder.setSource(service.advancedHospitalSearch(query));
		}
		
		model.put("pagination", this.pagedListHolder.getSource().size() > pagedListHolder.getPageSize()?true:false);
		model.put("pagedList", pagedListHolder);
		
		return "hospitals";
	}

	@RequestMapping(value="/hospitals/filter",method=RequestMethod.POST)
	public String renderFilteredHospitalsByPage(
			@Valid 
			@ModelAttribute("filter") HospitalFilterDTO dto,
			BindingResult results, 
			Map<String,Object> model) throws Exception{

		List<Hospital> hospitals = service.filterHospitalsByAddress(dto);

		if(hospitals.isEmpty()) 
			throw new HospitalControllerAdvice.FilterHospitalListEmptyException("Problem");
		else {
			pagedListHolder.setSource(hospitals);
			model.put("pagedList", pagedListHolder);
			model.put("pagination", true);
			if(results.hasErrors()){
				return "hospitals";
			}
			return "hospitals";
		}
	}

	@RequestMapping(value="/hospital/page/config",method=RequestMethod.GET)
	@ResponseBody
	public String configurePage(@RequestParam("itemPerPage") Integer itemPerPage,
			@RequestParam("type") String type){
		this.pagedListHolder.setPageSize(itemPerPage);
		this.pagedListHolder.setSort(new MutableSortDefinition("name", false, type.startsWith("Asc")));

		System.out.println("fuck ysdjak hadkjldaskadj jkladsjlkadsj jads jkasd jlk " + type);
		return "true";
	}


	@RequestMapping("/hospital/page/{page}")
	public String renderHospitalsByPage(Map<String,Object> model,
			@PathVariable("page") Integer currentPage
			){

		this.pagedListHolder.setPage(currentPage-1);
		model.put("pagedList", pagedListHolder);
		model.put("pagination", true);
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
}
