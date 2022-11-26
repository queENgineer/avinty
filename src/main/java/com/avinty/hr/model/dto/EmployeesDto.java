package com.avinty.hr.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class EmployeesDto {
	
	private Integer id;
	private String email;
	private String  password;
	private String fullName;
	private Integer departmentId;
	private Timestamp createdAt;
	private Integer createdBy;
	private Timestamp updatedAt;
	private Integer updatedBy;
	
}
