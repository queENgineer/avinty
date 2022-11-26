package com.avinty.hr.util;


import org.slf4j.helpers.MessageFormatter;

public class ConstantUtil {

	
	public ConstantUtil() {
	}
	
	public static String encode(String message, Object... arguments) {
		message = message.replace('\n', '_').replace('\r', '_').replace('\t', '_');
		return MessageFormatter.arrayFormat(message, arguments).getMessage();
	}
}
