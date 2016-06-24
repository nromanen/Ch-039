package com.hospitalsearch.validator;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Base64;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartFile;

@PropertySource(value = "classpath:uploader.properties")
public class ImageValidator {

	@Resource
	Environment properties;

	@Autowired
	MessageSource messageSource;

	private MultipartFile multipartFile;	
	private Locale locale;
	private String type;
	private String error;

	public boolean validate(MultipartFile multipartFile, String type) {
		this.multipartFile = multipartFile;
		this.type = type;
		locale = LocaleContextHolder.getLocale();
		if (!checkSize()) return false;
		if (!checkExtension()) return false;
		if (!checkSignature()) return false;
		if (!checkDimension()) return false;
		return true;		
	}

	public String getError() {
		return error;
	}
	
	private boolean checkSize() {
		Long FILE_MAX_SIZE = Long.parseLong(properties.getProperty(type + ".file.max.size"));
		if (multipartFile.getSize() > FILE_MAX_SIZE) {
			error = messageSource.getMessage("upload.image.filesize", null, locale);
			return false;
		}
		return true;
	}

	private boolean checkExtension() {
		String FILE_NAME_PATTERN = properties.getProperty(type + ".file.name.pattern");
		Pattern pattern = Pattern.compile(FILE_NAME_PATTERN);
		Matcher matcher = pattern.matcher(multipartFile.getOriginalFilename());
		if (! matcher.matches()) {
			error = messageSource.getMessage("upload.image.filetype", null, locale);
			return false;
		}
		return true;
	}

	private boolean checkSignature() {
		String fileName = multipartFile.getOriginalFilename();
		String extension = fileName.substring(fileName.length() - 3).toLowerCase();
		String MAGICK_NUMBER = properties.getProperty(extension + ".magick.number");
		byte [] magickNumber = Base64.getDecoder().decode(MAGICK_NUMBER);
		try {
			byte [] fileByte = multipartFile.getBytes();
			for (int i = 0; i < magickNumber.length; i++) {
				if (magickNumber[i] != fileByte[i]) {
					error = messageSource.getMessage("upload.image.broken", null, locale);
					return false;
				};
			}
		} catch (IOException e) {
			error =  messageSource.getMessage("upload.image.error", null, locale);
			return false;
		}
		return true;
	}

	private boolean checkDimension() {
		try {
			BufferedImage image = ImageIO.read(multipartFile.getInputStream());
			int IMAGE_MAX_HEIGHT = Integer.parseInt(properties.getProperty(type + ".image.max.height"));
			int IMAGE_MAX_WIDTH = Integer.parseInt(properties.getProperty(type + ".image.max.width"));
			int width = image.getWidth();
			int height = image.getHeight();
			if (width > IMAGE_MAX_WIDTH || height > IMAGE_MAX_HEIGHT ) {
				error = messageSource.getMessage("upload.image.dimension", null, locale);
				return false;
			}
		} catch (IOException e) {
			error = messageSource.getMessage("upload.image.error", null, locale);
			return false;
		}
		return true;
	}
}
