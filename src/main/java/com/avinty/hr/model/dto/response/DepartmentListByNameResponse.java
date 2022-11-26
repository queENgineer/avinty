package com.avinty.hr.model.dto.response;

import com.avinty.hr.model.dto.DepartmentsByNameDto;
import com.avinty.hr.model.dto.DepartmentsDto;
import lombok.Data;

import java.util.List;

@Data
public class DepartmentListByNameResponse {
	List<DepartmentsByNameDto> departmentsDtoList;
}
