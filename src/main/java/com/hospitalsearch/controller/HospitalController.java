package com.hospitalsearch.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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
    @Autowired(required = true)
    private HospitalService service;

    @Autowired(required = true)
    private DepartmentService departmentService;

    @Autowired(required = true)
    private DoctorInfoService doctorInfoService;

    private List<Hospital> currentHospitalList;
    private Page pageableContent;
    
    @RequestMapping("/")
    public String renderIndex(Map<String, Object> model) {

        return "layout";
    }



    @RequestMapping("/hospitals")
    public String renderHospitals(Map<String, Object> model,
            @RequestParam(value = "q", required = false) String query) throws ParseException, InterruptedException, FilterHospitalListEmptyException {
        if (query != null && !query.isEmpty()) {
                this.pageableContent = service.advancedHospitalSearch(query);
        }
        this.initializeModel(model, 1);
        if(this.pageableContent.getResultListCount() == 0){
        	throw new HospitalControllerAdvice.FilterHospitalListEmptyException("Empty list");
        }
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
            throw new HospitalControllerAdvice.FilterHospitalListEmptyException("Problem");
        } else {
            model.put("pagination", true);
            if (results.hasErrors()) {
                return "hospitals";
            }
            return "hospitals";
        }
    }

    @RequestMapping(value = "/hospitals/config", method = RequestMethod.POST)
    
    public String configurePage(Map<String,Object> model,
    		@ModelAttribute("pageConfig") 
    			PageConfigDTO config
           ) {
    	this.pageableContent.setPageSize(config.getItemsPerPage());

    	this.initializeModel(model, 1);
        return "hospitals";
    }

    @RequestMapping("/hospital/page/{page}")
    public String renderHospitalsByPage(Map<String, Object> model,
            @PathVariable("page") Integer currentPage
    ) {
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
    	model.put("pagedList", this.pageableContent.getPageList(page));
        model.put("pagination", this.pageableContent.isPaginated());
        model.put("pageCount", this.pageableContent.getPageCount());
        model.put("pageSize", this.pageableContent.getPageSize());
        model.put("currentPage", page);
        model.put("itemNumber", this.pageableContent.getResultListCount());
        model.put("pageConfig",new PageConfigDTO());
        model.put("sortType",this.pageableContent.getSortType());
    }
}
