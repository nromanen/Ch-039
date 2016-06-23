package com.hospitalsearch.util;

/**
 * @author Oleksandr Mukonin
 * Utitlity class with method that generates new file name
 * New file name formed using old one (trimmed to size of 8 symbols 
 * if needed) and added generated sequency if digits/letters to length
 * of 16 symbols and added original file extension 
 * 
 */

public class FileNameGenerator {

	/**
	 * @param fullName	old file name
	 * @return 			new file name
	 */
	public static String getNewFileName(String fullName) {
		String extension = getExt(fullName);
		String name = getName(fullName);
		String addition = java.util.UUID.randomUUID().toString().replaceAll("-", "");
		StringBuilder builder = new StringBuilder();
		builder.append((name.length() > 8) ? name.substring(0, 8) : name)
				.append(addition.substring(0, 16 - builder.length()))
				.append(extension);
		return builder.toString();
	}

	private static String getExt(String fullName) {
		if (fullName.lastIndexOf('.') > 0) {
			return fullName.substring(fullName.lastIndexOf('.'));
		}
		return "";
	}

	private static String getName(String fullName) {
		if (fullName.lastIndexOf('.') > 0) {
			return fullName.substring(0, fullName.lastIndexOf('.'));
		}
		return fullName;
	}
}
