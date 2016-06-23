package com.hospitalsearch.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hospitalsearch.service.FileUploadService;
import com.hospitalsearch.validator.ImageValidator;

/**
 * 
 * @author Oleksandr Mukonin
 *
 */

@PropertySource(value = "classpath:uploader.properties")
@Controller
public class FileUploadController {

	final static Logger log = Logger.getLogger(FileUploadController.class);

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ImageValidator imageValidator;

	@Autowired
	private FileUploadService service;

	@RequestMapping(value = {"/**/getparams/{paramid}"}, method = RequestMethod.POST)
	public @ResponseBody Map<String, String> messages (@PathVariable("paramid") String paramId, Locale locale) {
		Map<String, String> result = new HashMap<>();
		ResourceBundle resource = null;
		switch (paramId) {
		case "messages" : resource = ResourceBundle.getBundle("i18n/messages", locale);
		break;
		case "uploadparams" : resource = ResourceBundle.getBundle("uploader");
		break;
		}
		Enumeration<String> keys = resource.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			result.put(key, resource.getString(key));
		}
		return result;
	}

	@RequestMapping(value = {"/**/upload"}, method = RequestMethod.POST)
	public @ResponseBody String upload(@RequestParam("file") MultipartFile multipartFile, 
			@RequestParam("type") String type, HttpServletResponse response, Locale locale) throws IOException {
		boolean valid = imageValidator.validate(multipartFile, type);
		String error = imageValidator.getError();
		String result = valid ? service.save(multipartFile, type) : null;
		if (result == null) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			PrintWriter out = response.getWriter();
			out.println(valid ? messageSource.getMessage("upload.image.error", null, locale) : error);
			out.close();
			return null;
		}
		log.info("Uploaded new image, type: " + type + " new filename: " + result);
		return result;
	}
}
