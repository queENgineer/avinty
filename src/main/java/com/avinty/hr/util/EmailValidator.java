package com.avinty.hr.util;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {
	
	private static final String REGEX_VALUE = "^[_A-Za-z0-9-]+(.[_A-Za-z0-9-]+)@[A-Za-z0-9-]+(.[A-Za-z0-9-]+)(.[A-Za-z]{2,})$";
	private static final Pattern pattern = Pattern.compile(REGEX_VALUE);
	
	public static boolean isNOTValid(String email) {
		return !isValid(email);
	}
	
	public static boolean isValid(String email) {
		
		if (!StringUtils.hasText(email)) {
			return false;
		}
		Matcher m = pattern.matcher(email);
		return m.find();
	}
	
}