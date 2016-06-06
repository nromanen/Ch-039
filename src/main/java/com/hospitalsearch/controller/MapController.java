package com.hospitalsearch.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospitalsearch.entity.Hospital;
import com.hospitalsearch.entity.HospitalAddress;
import com.hospitalsearch.service.HospitalService;
import com.hospitalsearch.util.Bounds;

/**
 *
 * @author Oleksandr Mukonin
 *
 */
@Controller
public class MapController {

	@Autowired
	HospitalService service;

	@Autowired
	MessageSource messageSource;

	@RequestMapping(value = { "/mapsearch"}, method = RequestMethod.GET)
	public String list() {
		return "welcome";
	}

	@ResponseBody
	@RequestMapping(value = { "/**/getmarkers"}, method = RequestMethod.POST)
	public List<Hospital> getMarkers(@RequestBody Bounds bounds){
		return service.getAllByBounds(bounds);
	}

	@PreAuthorize ("hasRole('ADMIN')")
	@RequestMapping(value = { "/admin/map/validate"}, method = RequestMethod.POST)
	public String newHospitalFromMap(@RequestParam("hospjs") String data,
			RedirectAttributes redirectAttributes) {		
		try {
			ObjectMapper mapper = new ObjectMapper();
			Hospital hospital = mapper.readValue(data, Hospital.class);
			redirectAttributes.addFlashAttribute("hospital", hospital);
			return "redirect:/admin/map/new";
		} catch (Exception e) {

		}
		return "redirect:/admin/map/validate";
	}

	@PreAuthorize ("hasRole('ADMIN')")
	@RequestMapping(value = {"/admin/map/listhospitals" }, method = RequestMethod.GET)
	public String listHospitals(ModelMap model) {
		List<Hospital> hospitals = service.getAll();
		model.addAttribute("hospitals", hospitals);
		return "maplistadmin";
	}

	@PreAuthorize ("hasRole('ADMIN')")
	@RequestMapping(value = { "/admin/map/new" }, method = RequestMethod.GET)
	public String newHospital(ModelMap model) {
		Hospital hospital = new Hospital();
		HospitalAddress address = new HospitalAddress();
		hospital.setAddress(address);
		model.putIfAbsent("hospital", hospital);
		return "mapadmin";
	}

	@PreAuthorize ("hasRole('ADMIN')")
	@RequestMapping(value = { "/admin/map/new" }, method = RequestMethod.POST)
	public String saveHospital(@Valid Hospital hospital, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "mapadmin";
		}
		service.save(hospital);
		redirectAttributes.addFlashAttribute("message", "Hospital: \"" +
				hospital.getName() + "\" information added");
		return "redirect:/admin/map/listhospitals";
	}

	@PreAuthorize ("hasRole('ADMIN')")
	@RequestMapping(value = { "/admin/map/edithospital/{id}" }, method = RequestMethod.GET)
	public String editHosital(@PathVariable long id, ModelMap model) {
		Hospital hospital = service.getById(id);
		model.addAttribute("hospital", hospital);
		return "mapadmin";
	}

	@PreAuthorize ("hasRole('ADMIN')")
	@RequestMapping(value = { "/admin/map/edithospital/{id}" }, method = RequestMethod.POST)
	public String updateHospital(@Valid Hospital hospital, BindingResult result,
			@PathVariable long id, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "mapadmin";
		}
		service.update(hospital);
		redirectAttributes.addFlashAttribute("message", "Hospital: \"" +
				hospital.getName() + "\" information updated");
		return "redirect:/admin/map/listhospitals";
	}

	@PreAuthorize ("hasRole('ADMIN')")
	@RequestMapping(value = { "/admin/map/deletehospital/{id}" }, method = RequestMethod.POST)
	public String deleteHospital(@PathVariable long id, RedirectAttributes redirectAttributes) {
		Hospital hospital = service.getById(id);
		service.deleteById(id);
		redirectAttributes.addFlashAttribute("message", "Hospital: \"" + hospital.getName() + "\" information deleted");
		return "redirect:/admin/map/listhospitals";
	}

	@PreAuthorize ("hasRole('ADMIN')")
	@RequestMapping(value = { "/admin/map/validate" }, method = RequestMethod.GET)
	public String validate() {
		return "mapvalidation";
	}

}