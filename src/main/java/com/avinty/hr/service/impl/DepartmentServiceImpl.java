package com.avinty.hr.service.impl;

import com.avinty.hr.enums.EnumWebServiceUser;
import com.avinty.hr.exception.InvalidParameterException;
import com.avinty.hr.model.dto.DepartmentsByNameDto;
import com.avinty.hr.model.dto.DepartmentsDto;
import com.avinty.hr.model.dto.EmployeesDto;
import com.avinty.hr.model.entity.Departments;
import com.avinty.hr.model.entity.Employees;
import com.avinty.hr.repo.DepartmentRepository;
import com.avinty.hr.repo.EmployeeRepository;
import com.avinty.hr.service.DepartmentService;
import com.avinty.hr.util.ReturnCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.PreDestroy;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.avinty.hr.util.ConstantUtil.encode;

@Service
@Slf4j
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
	@Autowired
	private DepartmentRepository departmentRepository;
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public List<DepartmentsDto> findAll(String txnId) {
		log.info(encode("{} DepartmentServiceImpl - findAll() started", txnId));
		
		List<DepartmentsDto>  departmentsDtoList = new ArrayList<>();
		ModelMapper modelMapper=new ModelMapper();
		
		try {
			Iterable<Departments> iterable = departmentRepository.findAll();
			if (iterable != null) {
				for (Departments entity : iterable) {
					DepartmentsDto departmentsDto=modelMapper.map(entity,DepartmentsDto.class);
					List<EmployeesDto> employeesDtoList=entity.getEmployeelist().stream()
						.map(emp -> modelMapper.map(emp, EmployeesDto.class))
						.collect(Collectors.toList());
					departmentsDto.setEmployeesDtoList(employeesDtoList);
					departmentsDtoList.add(departmentsDto);
				}
			}
			
			log.info(encode("{} DepartmentServiceImpl - findAll() finished successfully", txnId));
			return departmentsDtoList;
		} catch (Exception e) {
			log.error(encode("{} DepartmentServiceImpl - findAll() has an error: {}", txnId, e.getMessage()));
			throw e;
		}
	}
	
	@Override
	public List<DepartmentsByNameDto> findByName(String txnId, String name) {
		log.info(encode("{} DepartmentServiceImpl - findByName() started", txnId));
		
		List<DepartmentsByNameDto>  departmentsDtoList = new ArrayList<>();
		ModelMapper modelMapper=new ModelMapper();
		
		try {
			Iterable<Departments> iterable = departmentRepository.findDepartmentsByNameContaining(name);
			if (iterable != null) {
				for (Departments entity : iterable) {
					DepartmentsByNameDto departmentsDto=modelMapper.map(entity,DepartmentsByNameDto.class);
					departmentsDtoList.add(departmentsDto);
				}
			}
			
			log.info(encode("{} DepartmentServiceImpl - findByName() finished successfully", txnId));
			return departmentsDtoList;
		} catch (Exception e) {
			log.error(encode("{} DepartmentServiceImpl - findByName() has an error: {}", txnId, e.getMessage()));
			throw e;
		}
	}
	
	@Override
	@Transactional(propagation =Propagation.REQUIRED)
	public Boolean deleteById(String txnId, Integer id) {
		log.info(encode("{} DepartmentServiceImpl - deleteById() started", txnId));
		ModelMapper modelMapper=new ModelMapper();
		try {
			Iterable<Departments>  iterable = departmentRepository.findDepartmentsById(id);//not threadsafe
		
			if(iterable!=null){
				for (Departments departments : iterable) {
					List<EmployeesDto> employeesDtoList=departments.getEmployeelist().stream()
						.map(emp -> modelMapper.map(emp, EmployeesDto.class))
						.collect(Collectors.toList());
					
					for(EmployeesDto dto:employeesDtoList){
						dto.setDepartmentId(null);
						dto.setUpdatedAt(Timestamp.from(ZonedDateTime.now().toInstant()));
						
						dto.setUpdatedBy(EnumWebServiceUser.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().toUpperCase(
							Locale.ROOT)).getCode());
						
						employeeRepository.save(modelMapper.map(dto,Employees.class));
						departmentRepository.deleteDepartmentsById(departments.getId());
					}
				}
				
			}else{
				throw new InvalidParameterException(ReturnCode.INVALID_DEPARTMENTID);
			}
			log.info(encode("{} DepartmentServiceImpl - deleteById() finished successfully", txnId));
			return true;
		} catch (Exception e) {
			log.error(encode("{} DepartmentServiceImpl - deleteById() has an error: {}", txnId, e.getMessage()));
			throw e;
		}
	}
	
	

	
}
