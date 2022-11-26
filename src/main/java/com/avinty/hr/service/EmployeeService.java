package com.avinty.hr.service;

import com.avinty.hr.model.dto.EmployeesDto;
import com.avinty.hr.model.dto.request.SaveEmployeeRequest;

import java.util.List;

public interface EmployeeService {
	List<EmployeesDto> findAll(String txnId);
	EmployeesDto saveEmployees(String txnId, SaveEmployeeRequest request);
}