package com.avinty.hr.service.impl;

import com.avinty.hr.enums.EnumWebServiceUser;
import com.avinty.hr.model.dto.EmployeesDto;
import com.avinty.hr.model.dto.request.SaveEmployeeRequest;
import com.avinty.hr.model.entity.Employees;
import com.avinty.hr.repo.EmployeeRepository;
import com.avinty.hr.service.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.avinty.hr.util.ConstantUtil.encode;

@Service
@Slf4j
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeRepository repo;
	
	@Override
	public List<EmployeesDto> findAll(String txnId) {
		log.info(encode("{} EmployeeServiceImpl - findAll() started. ", txnId));
		
		List<EmployeesDto>  employeesDtoList = new ArrayList<>();
		ModelMapper modelMapper=new ModelMapper();
		
		try {
			Iterable<Employees> iterable = repo.findAll();
			if (iterable != null) {
				for (Employees entity : iterable) {
					employeesDtoList.add(modelMapper.map(entity,EmployeesDto.class));
				}
			}
			
			log.info(encode("{} EmployeeServiceImpl - findAll() finished successfully. ", txnId));
			return employeesDtoList;
		} catch (Exception e) {
			log.error(encode("{} EmployeeServiceImpl - findAll() has an error: {}", txnId, e.getMessage()));
			return null;
		}
	}
	
	@Override
	public EmployeesDto saveEmployees(String txnId, SaveEmployeeRequest request) {
		log.info(encode("{} EmployeeServiceImpl - findAll() started. ", txnId));
		
		EmployeesDto employeesDto=new EmployeesDto();
		employeesDto.setEmail(request.getEmail());
		employeesDto.setDepartmentId(request.getDepartmentId());
		employeesDto.setFullName(request.getFullName());
		employeesDto.setEmail(request.getEmail());
		employeesDto.setPassword(request.getPassword());
		employeesDto.setCreatedAt(Timestamp.from(ZonedDateTime.now().toInstant()));
		employeesDto.setCreatedBy(
			EnumWebServiceUser.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().toUpperCase(
				Locale.ROOT)).getCode());
		ModelMapper modelMapper=new ModelMapper();
		Employees entity=	repo.save(modelMapper.map(employeesDto,Employees.class));
		
		log.info(encode("{} EmployeeServiceImpl - findAll() started. ", txnId));
		
		
		return modelMapper.map(entity,EmployeesDto.class);
	}
}
