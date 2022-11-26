package com.avinty.hr.enums;

import com.avinty.hr.util.ReturnCode;

import java.util.LinkedHashMap;
import java.util.Map;

public enum EnumWebServiceUser {
	
	ADMIN(0),
	USER(1);
	
	private static final Map<Integer, EnumWebServiceUser> LOOKUP_MAP = new LinkedHashMap<>();
	
	static {
		for (EnumWebServiceUser returnCode : values()) {
			LOOKUP_MAP.put(returnCode.getCode(), returnCode);
		}
	}
	
	private final int code;
	
	private EnumWebServiceUser(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	public static EnumWebServiceUser getFromName(int code) {
		return LOOKUP_MAP.get(code);
	}
}
