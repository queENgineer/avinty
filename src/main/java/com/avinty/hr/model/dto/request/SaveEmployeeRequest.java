package com.avinty.hr.model.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SaveEmployeeRequest {
	
	@JsonProperty("email")
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private String email;
	
	@JsonProperty("password")
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private String  password;
	
	@JsonProperty("fullName")
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private String fullName;
	
	@JsonProperty("departmentId")
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private Integer departmentId;

}
