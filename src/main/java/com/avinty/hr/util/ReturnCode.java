package com.avinty.hr.util;


import java.util.LinkedHashMap;
import java.util.Map;

public enum ReturnCode {
	
	// General
	SUCCESS(0), FAILED(-1), INTERNAL_ERROR(-999)
	,REQUEST_BODY_CANNOT_BE_EMPTY(1000)
	,DEPARTMENT_NOT_FOUND(1001)
	,EMAIL_CANNOT_BE_EMPTY(1002)
	,INVALID_EMAIL(1003)
	,FULLNAME_CANNOT_BE_EMPTY(1004)
	,INVALID_FULLNAME(1005)
	,PASSWORD_CANNOT_BE_EMPTY(1004)
	,INVALID_PASSWORD(1005)
	,DEPARTMENTID_CANNOT_BE_EMPTY(1006)
	,INVALID_DEPARTMENTID(1007)
	,ACCESS_DENIED(1008)
	;
	
	private static final Map<Integer, ReturnCode> LOOKUP_MAP = new LinkedHashMap<>();
	
	static {
		for (ReturnCode returnCode : values()) {
			LOOKUP_MAP.put(returnCode.getCode(), returnCode);
		}
	}
	
	private final int code;
	
	private ReturnCode(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	public static ReturnCode getFromCode(int code) {
		return LOOKUP_MAP.get(code);
	}
}
