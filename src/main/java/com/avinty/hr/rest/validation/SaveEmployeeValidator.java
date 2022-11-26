package com.avinty.hr.rest.validation;

import com.avinty.hr.exception.InvalidParameterException;
import com.avinty.hr.model.RequestBean;
import com.avinty.hr.model.dto.EmployeesDto;
import com.avinty.hr.model.dto.request.SaveEmployeeRequest;
import com.avinty.hr.model.entity.Departments;
import com.avinty.hr.repo.DepartmentRepository;
import com.avinty.hr.util.EmailValidator;
import com.avinty.hr.util.PasswordValidator;
import com.avinty.hr.util.ReturnCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.Column;
import java.util.List;

import static com.avinty.hr.util.ConstantUtil.encode;

@Component
@Slf4j
public class SaveEmployeeValidator implements Validator {
	
//	private final static Logger logger = LoggerFactory.getLogger(CreateCustomerRequestValidator.class);
	
	@Override
	public boolean supports(Class<?> clazz) {
		return SaveEmployeeRequest.class.equals(clazz);
	}
	
	@Autowired
	RequestBean requestBean;
	
	@Autowired
	DepartmentRepository departmentRepository;
	
	@Override
	public void validate(Object o, Errors errors) {
		SaveEmployeeRequest request = (SaveEmployeeRequest) o;
		
		String logMsg="CreateCustomerRequestValidator - ";
		try {
			if (ObjectUtils.isEmpty(request)) {
				log.info(encode(" SaveEmployeeValidator - Request object cannot be empty!"));
				throw new InvalidParameterException(ReturnCode.REQUEST_BODY_CANNOT_BE_EMPTY.name());
				
			}
			
			if(ObjectUtils.isEmpty(request.getEmail())){
				log.info(encode(" SaveEmployeeValidator - email  cannot be empty!"));
				throw new InvalidParameterException(ReturnCode.EMAIL_CANNOT_BE_EMPTY);
			}else{
				if(!EmailValidator.isValid(request.getEmail())) {
					log.info(encode(" SaveEmployeeValidator - invalid email!"));
					throw new InvalidParameterException(ReturnCode.INVALID_EMAIL);
				}
			}
				
				
				if(ObjectUtils.isEmpty(request.getFullName())){
					log.info(encode(" SaveEmployeeValidator - full name  cannot be empty!"));
					throw new InvalidParameterException(ReturnCode.FULLNAME_CANNOT_BE_EMPTY);
				}else {
					if (!StringUtils.hasText(request.getFullName())) {
						log.info(encode(" SaveEmployeeValidator - invalid full name!"));
						throw new InvalidParameterException(ReturnCode.INVALID_FULLNAME);
					}
				}

			
			
			if(ObjectUtils.isEmpty(request.getPassword())){
				log.info(encode(" SaveEmployeeValidator - password  cannot be empty!"));
				throw new InvalidParameterException(ReturnCode.PASSWORD_CANNOT_BE_EMPTY);
			}else {
				if (!StringUtils.hasText(request.getPassword()) ||
					!PasswordValidator.isValidPassword(request.getPassword())) {
					log.info(encode(" SaveEmployeeValidator - invalid password!"));
					throw new InvalidParameterException(ReturnCode.INVALID_PASSWORD);
				}
			}
				
				
				if (ObjectUtils.isEmpty(request.getDepartmentId())) {
					log.info(encode(" SaveEmployeeValidator - department id  cannot be empty!"));
					throw new InvalidParameterException(ReturnCode.DEPARTMENTID_CANNOT_BE_EMPTY);
				} else {
					if(NumberUtils.isParsable(String.valueOf(request.getDepartmentId()))){
						List<Departments> departments=departmentRepository.findDepartmentsById(request.getDepartmentId());
						if(ObjectUtils.isEmpty(departments)){
							log.info(encode(" SaveEmployeeValidator - invalid department id!"));
							throw new InvalidParameterException(ReturnCode.INVALID_DEPARTMENTID);
						}
					}else{
						log.info(encode(" SaveEmployeeValidator - invalid department id!"));
						throw new InvalidParameterException(ReturnCode.INVALID_DEPARTMENTID);
					}
				}
		}catch (InvalidParameterException e){
			throw e;
		}catch (Exception e){
			log.info(encode(" SaveEmployeeValidator - Exception occured : {}",e));
		}
	}
}
