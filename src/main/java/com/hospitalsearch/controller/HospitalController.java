package com.hospitalsearch.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hospitalsearch.controller.advice.HospitalControllerAdvice;
import com.hospitalsearch.controller.advice.HospitalControllerAdvice.FilterHospitalListEmptyException;
import com.hospitalsearch.entity.Department;
import com.hospitalsearch.entity.Hospital;
import com.hospitalsearch.service.DepartmentService;
import com.hospitalsearch.service.DoctorInfoService;
import com.hospitalsearch.service.HospitalService;
import com.hospitalsearch.util.HospitalFilterDTO;
import com.hospitalsearch.util.Page;
import com.hospitalsearch.util.PageConfigDTO;

@Controller
public class HospitalController {
	private final Logger LOG = LogManager.getLogger(HospitalController.class);
    @Autowired(required = true)
    private HospitalService service;
    @Autowired(required = true)
    private DepartmentService departmentService;
    @Autowired(required = true)
    private DoctorInfoService doctorInfoService;
    
    private Page<Hospital> pageableContent;
    private Integer pageNumber;
    private String searchQuery;
    
    @RequestMapping("/")
    public String renderIndex(Map<String, Object> model) {
        return "layout";
    }
 
    @RequestMapping("/hospitals")
    public String renderHospitals(Map<String, Object> model,
            @RequestParam(value = "q", required = false) String query) throws ParseException, InterruptedException, FilterHospitalListEmptyException {
    	this.searchQuery = query;
    	this.pageNumber = 1;
    	if (query != null && !query.isEmpty()) {
                this.pageableContent = service.advancedHospitalSearch(query,10,pageNumber,true);
        }
        this.initializeModel(model, 1);
        if(this.pageableContent.getResultListCount() == 0){
        	throw new HospitalControllerAdvice.FilterHospitalListEmptyException(this.searchQuery);
        }
        LOG.log(Level.ALL,"lol");
        return "hospitals";
    }

    @RequestMapping(value = "/hospitals/filter", method = RequestMethod.POST)
    public String renderFilteredHospitalsByPage(
            @Valid
            @ModelAttribute("filter") HospitalFilterDTO dto,
            BindingResult results,
            Map<String, Object> model) throws Exception {

        List<Hospital> hospitals = service.filterHospitalsByAddress(dto);
        if (hospitals.isEmpty()) {
            throw new HospitalControllerAdvice.FilterHospitalListEmptyException(this.searchQuery);
        } else {
            model.put("pagination", true);
            if (results.hasErrors()) {
                return "hospitals";
            }
            return "hospitals";
        }
    }

    @RequestMapping(value = "/hospitals/config", method = RequestMethod.GET)
    public String configurePage(Map<String,Object> model,
    		@ModelAttribute("pageConfig") 
    			PageConfigDTO config) throws ParseException, InterruptedException {
    	if(this.pageableContent.getPageSize() != config.getItemsPerPage() && this.pageNumber > this.pageableContent.getResultListCount() / config.getItemsPerPage()) {
    		this.pageNumber = this.pageableContent.getResultListCount() / config.getItemsPerPage();
    		if(this.pageableContent.getResultListCount() % config.getItemsPerPage() > 0) this.pageNumber++;
    	}
    	this.pageableContent = service.advancedHospitalSearch(this.searchQuery, config.getItemsPerPage(), this.pageNumber, config.getSortType());
    	this.pageableContent.makeSort();
    	this.initializeModel(model, this.pageNumber);
        return "hospitals";
    }

    @RequestMapping("/hospital/page/{page}")
    public String renderHospitalsByPage(Map<String, Object> model,
            @PathVariable("page") Integer currentPage
    ) throws ParseException, InterruptedException { 
    	this.pageNumber = currentPage;
    	this.pageableContent = service.advancedHospitalSearch(this.searchQuery, this.pageableContent.getPageSize(), this.pageNumber, this.pageableContent.getSortType());
    	this.initializeModel(model, currentPage);
        return "hospitals";
    }

    @RequestMapping("/hospital/{id}")
    public String renderDepartments(Map<String, Object> model,
            @PathVariable Long id
    ) {
        List<Department> lst = departmentService.findByHospitalId(id);

        model.put("departments", lst);
        model.put("hospital", service.getById(id));
        model.put("hid", id);
        return "departments";
    }
    @RequestMapping("/hospital/{hid}/department/{id}")
    public String renderDoctors(Map<String, Object> model,
            @PathVariable Long hid,
            @PathVariable Long id
    ) {
        Department d = departmentService.getById(id);
        model.put("doctors", doctorInfoService.findByDepartmentId(id));
        model.put("department", d);
        model.put("hospital", d.getHospital());
        model.put("hid", hid);
        model.put("id", id);
        return "doctors";
    }
    
    public void initializeModel(Map<String,Object> model,Integer page){
    	model.put("pagedList", this.pageableContent.getPageItems());
    	model.put("query", this.searchQuery);
        model.put("pagination", this.pageableContent.isPaginated());
        model.put("pageCount", this.pageableContent.getPageCount());
        model.put("pageSize", this.pageableContent.getPageSize());
        model.put("currentPage", page);
        model.put("itemNumber", this.pageableContent.getResultListCount());
        model.put("pageConfig",new PageConfigDTO());
        model.put("sort",this.pageableContent.getSortType());
    }
}
