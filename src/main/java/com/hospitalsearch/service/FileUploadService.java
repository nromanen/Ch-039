package com.hospitalsearch.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.hospitalsearch.util.FileNameGenerator;

@Component
public class FileUploadService {

	final static Logger log = Logger.getLogger(FileUploadService.class);
	
	@Resource
	private Environment properties;

	public String save(MultipartFile multipartFile, String type) {
		String fileName = multipartFile.getOriginalFilename();
		String newFileName = FileNameGenerator.getNewFileName(fileName);
		String path = new StringBuilder()
				.append(System.getProperty(properties.getProperty("global.root.path"))).append(File.separator)
				.append(properties.getProperty("global.resource.path")).append(File.separator)
				.append(properties.getProperty("global.image.path")).append(File.separator)
				.append(properties.getProperty(type + ".image.path")).append(File.separator)
				.append(newFileName)
				.toString();		
		try (OutputStream out = new FileOutputStream(path)) {
			byte[] fileByte = multipartFile.getBytes();
			out.write(fileByte);
		} catch (IOException e) {
			log.error("Error savong image file to filesystem, error: " + e.getMessage());
			return null;
		}
		return newFileName;
	}
}
