package com.avinty.hr.service;

import com.avinty.hr.model.dto.DepartmentsByNameDto;
import com.avinty.hr.model.dto.DepartmentsDto;
import com.avinty.hr.model.dto.EmployeesDto;
import java.util.List;
	
public interface DepartmentService {
	List<DepartmentsDto> findAll(String txnId);
	List<DepartmentsByNameDto> findByName(String txnId, String name);
	Boolean deleteById(String txnId, Integer id);
}