package com.avinty.hr.exception;


import com.avinty.hr.util.ReturnCode;

public class InvalidParameterException extends RuntimeException {
	
	 int errorCode;
	 String errorDesc;
	
	private static final long serialVersionUID = -5975800654092070384L;
	
	public InvalidParameterException() {
		super();
	}
	
	public InvalidParameterException(String message, Throwable cause, boolean enableSuppression,
									 boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	public InvalidParameterException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public InvalidParameterException(String message) {
		super(message);
	}
	
	public InvalidParameterException(Throwable cause) {
		super(cause);
	}
	
	public InvalidParameterException(int errorCode, String errorDesc) {
		this.errorCode=errorCode;
		this.errorDesc=errorDesc;
	}
	
	public InvalidParameterException(ReturnCode returnCode) {
		this(returnCode.getCode(), returnCode.name());
	}
	
	public InvalidParameterException(int errorCode, String errorDesc, String message) {
		super(message);
		this.errorCode=errorCode;
		this.errorDesc=errorDesc;
	}
	
	public int getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getErrorDesc() {
		return errorDesc;
	}
	
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	
	
}