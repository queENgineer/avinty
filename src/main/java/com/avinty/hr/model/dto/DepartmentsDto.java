package com.avinty.hr.model.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
public class DepartmentsDto {
	private Integer id;
	private String name;
	private Integer managerId;
	private Timestamp createdAt;
	private Integer createdBy;
	private Timestamp updatedAt;
	private Integer updatedBy;
	private List<EmployeesDto> employeesDtoList;
	
}
