package com.hospitalsearch.controller;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author Oleksandr Mukonin
 *
 */

@Controller
public class FileUploadController {
	
	private static long FILE_SIZE = 204800;
	private static String FILE_NAME_PATTERN = "([^\\s]+(\\.(?i)(jpg|png))$)";
	private static int IMAGE_MAX_HEIGHT = 400;
	private static int IMAGE_MAX_WIDTH = 400;
	
	@Autowired
	MessageSource messageSource;

	@RequestMapping(value = {"/**/upload"}, method = RequestMethod.POST)
	public @ResponseBody String upload(@RequestParam("file") MultipartFile multipartFile, 
			@RequestParam("typePath") String typePath, HttpServletRequest request, 
			HttpServletResponse response, Locale locale) throws IOException {
		
		ImageChecker checker = new ImageChecker();
		checker.setMultipartFile(multipartFile);
		checker.setLocale(locale);
		checker.check();
		if (! checker.isValid()) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		    PrintWriter out = response.getWriter();
		    out.println(checker.getError());
		    out.close();
		    return null;			
		}

		String newFileName = genNewFilename( multipartFile.getOriginalFilename());
		
		String path = new StringBuilder()
				.append(request.getServletContext().getRealPath("/"))
				.append("resources/img/")
				.append(typePath).append("/")
				.append(newFileName)
				.toString();
		byte[] file = multipartFile.getBytes();
		try (OutputStream out = new FileOutputStream(path)) {
		out.write(file);
		out.close();
		} catch (IOException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		    PrintWriter out = response.getWriter();
		    out.println(messageSource.getMessage("upload.image.error", null, locale));
		    out.close();
		    return null;	
		}
		return newFileName;
	}

	private String genNewFilename(String fileName) {
		String extention = fileName.substring(fileName.length() - 4, fileName.length());
		String addition = java.util.UUID.randomUUID().toString().replaceAll("-", "");
		StringBuilder builder = new StringBuilder(); 
		if (fileName.length() > 12) {
			builder.append(fileName.substring(0, 8));
		} else {
			builder.append(fileName.substring(0, fileName.length() - 4));
		}
		return builder.append(addition.substring(0, 16 - builder.length()))
				.append(extention)
				.toString();
	}

	private class ImageChecker {
		
		private MultipartFile multipartFile;
		private boolean valid = false;
		private String error;
		private Locale locale;		
		
		public void setLocale(Locale locale) {
			this.locale = locale;
		}

		public void setMultipartFile(MultipartFile multipartFile) {
			this.multipartFile = multipartFile;
		}
		
		public boolean isValid() {
			return valid;
		}
		
		public String getError() {
			return error;
		}
		
		public void check() throws IOException {
			
			valid = true;
			
			if (multipartFile.getSize() > FILE_SIZE) {
				valid = false;
				error = messageSource.getMessage("upload.image.filesize", null, locale);
				return;
			}

			Pattern pattern = Pattern.compile(FILE_NAME_PATTERN);
			Matcher matcher = pattern.matcher(multipartFile.getOriginalFilename());
			if (! matcher.matches()) {
				valid = false;
				error = messageSource.getMessage("upload.image.filetype", null, locale);
				return;				
			}
			
			BufferedImage image = ImageIO.read(multipartFile.getInputStream());
			int width = image.getWidth();
			int height = image.getHeight();
			if (width > IMAGE_MAX_WIDTH || height > IMAGE_MAX_HEIGHT ) {
				valid = false;
				error = messageSource.getMessage("upload.image.dimension", null, locale);
				return;				
			}
		}		
	}
}
