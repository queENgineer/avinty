package com.avinty.hr.rest.controller;


import com.avinty.hr.model.dto.EmployeesDto;
import com.avinty.hr.model.dto.request.SaveEmployeeRequest;
import com.avinty.hr.model.dto.response.EmployeeListResponse;
import com.avinty.hr.model.dto.response.SaveEmployeeResponse;
import com.avinty.hr.rest.validation.SaveEmployeeValidator;
import com.avinty.hr.service.impl.EmployeeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

import static com.avinty.hr.util.ConstantUtil.encode;
import static com.avinty.hr.util.LocalConstantUtil.gson;

@Slf4j
@RequestMapping("/API/V1")
@RestController
//@CrossOrigin(origins = {"http://localhost:5000"})
public class EmployeeController {
	
	@Autowired private EmployeeServiceImpl employeeService;
	@Autowired private SaveEmployeeValidator saveEmployeeValidator;
	
	@InitBinder("saveEmployeeRequest")
	protected void initSaveEmployeeRequest(WebDataBinder binder) {
		binder.setValidator(this.saveEmployeeValidator);
	}
	
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	@RequestMapping(path = "/employees",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeListResponse> getEmployeeList(){
		String txnId = UUID.randomUUID().toString();
		
		log.info(encode("{} EmployeeController - getEmployeeList() started.", txnId));
		EmployeeListResponse response=new EmployeeListResponse();
		try {
			
			response.setEmployeesDtoList(employeeService.findAll(txnId));
			
			log.info(encode("{} EmployeeController - getEmployeeList() finished: {}", txnId, gson.toJson(response)));
			
		} catch (Exception e) {
			log.error(encode("{} EmployeeController - getEmployeeList() has an error: {}", txnId, e.toString()));
			throw e;
		}
		return ResponseEntity.ok(response);
	}
	
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(path = "/employees",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SaveEmployeeResponse> saveEmployee(@Valid @RequestBody SaveEmployeeRequest saveEmployeeRequest){
		String txnId = UUID.randomUUID().toString();
		
		log.info(encode("{} EmployeeController - saveEmployee() started.", txnId));
		SaveEmployeeResponse response=new SaveEmployeeResponse();
		try {
			EmployeesDto employees=employeeService.saveEmployees(txnId,saveEmployeeRequest);
			response.setEmployeesDto(employees);
			log.info(encode("{} EmployeeController - saveEmployee() finished: {}", txnId, gson.toJson(response)));
			
		} catch (Exception e) {
			log.error(encode("{} EmployeeController - saveEmployee() has an error: {}", txnId, e.toString()));
			throw e;
		}
		return ResponseEntity.ok(response);
	}
}
