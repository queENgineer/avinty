package com.avinty.hr.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDetail {
	
	private int errorcode;
	
	private String errordefinition;
	
	private String errormessage;
	
	public ErrorDetail() {
	}
	
	public ErrorDetail(int errorcode, String errordefinition) {
		this.errorcode = errorcode;
		this.errordefinition = errordefinition;
	}
	
	public int getErrorcode() {
		return errorcode;
	}
	
	public void setErrorcode(int errorcode) {
		this.errorcode = errorcode;
	}
	
	public String getErrordefinition() {
		return errordefinition;
	}
	
	public void setErrordefinition(String errordefinition) {
		this.errordefinition = errordefinition;
	}
	
	public String getErrormessage() {
		return errormessage;
	}
	
	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}
	
	@Override
	public String toString() {
		return "ErrorDetail{" +
			"errorcode=" + errorcode +
			", errordefinition='" + errordefinition + ' ' +
		", errormessage='" + errormessage + ' '+
		'}';
	}
}