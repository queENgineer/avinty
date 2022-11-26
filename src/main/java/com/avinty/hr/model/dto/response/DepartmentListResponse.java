package com.avinty.hr.model.dto.response;

import com.avinty.hr.model.dto.DepartmentsDto;
import com.avinty.hr.model.dto.EmployeesDto;
import lombok.Data;

import java.util.List;

	@Data
	public class DepartmentListResponse {
		List<DepartmentsDto> departmentsDtoList;
	}
