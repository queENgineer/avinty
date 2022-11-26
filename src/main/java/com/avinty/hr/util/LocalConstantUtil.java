package com.avinty.hr.util;

import com.google.gson.Gson;

import java.time.format.DateTimeFormatter;

public class LocalConstantUtil {
	
	public static final Gson gson = new Gson();
	
	//ENDPOINT
	public static final String ENDPOINT_ANNIVERSARY = "/anniversary";
	public static final String ENDPOINT_TES = "/tes";
	public static final String ENDPOINT_OPERATION = "/operation";
	public static final String ENDPOINT_MAKSELLENT = "/maksellentScenarioList";
	
	//TES
	public static final Integer TES_SUCCESS_CODE = 0;
	
	public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
}