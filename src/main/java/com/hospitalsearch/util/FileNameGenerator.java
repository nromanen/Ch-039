package com.hospitalsearch.util;

public class FileNameGenerator {
	
	public static String getNewFileName(String fileName) {
		String extension = fileName.substring(fileName.length() - 4);
		String addition = java.util.UUID.randomUUID().toString().replaceAll("-", "");
		StringBuilder builder = new StringBuilder(); 
		if (fileName.length() > 12) {
			builder.append(fileName.substring(0, 8));
		} else {
			builder.append(fileName.substring(0, fileName.length() - 4));
		}
		return builder.append(addition.substring(0, 16 - builder.length()))
				.append(extension)
				.toString();
	}
}
