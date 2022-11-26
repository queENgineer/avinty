package com.avinty.hr.rest.controller;

import com.avinty.hr.model.dto.response.DeleteDepartmentByIdResponse;
import com.avinty.hr.model.dto.response.DepartmentListByNameResponse;
import com.avinty.hr.model.dto.response.DepartmentListResponse;
import com.avinty.hr.service.impl.DepartmentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.avinty.hr.util.ConstantUtil.encode;
import static com.avinty.hr.util.LocalConstantUtil.gson;

@Slf4j
@RequestMapping("/API/V1")
@RestController
//@CrossOrigin(origins = {"http://localhost:5000"})
public class DepartmentController {
	
	@Autowired
	private DepartmentServiceImpl departmentService;
	
	@RequestMapping(path = "/dept-emp",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DepartmentListResponse> getDepartmentList(){
		String txnId = UUID.randomUUID().toString();
		
		log.info(encode("{} DepartmentController - getDepartmentList() started.", txnId));
		DepartmentListResponse response=new DepartmentListResponse();
		try {
			response.setDepartmentsDtoList(departmentService.findAll(txnId));
			log.info(encode("{} DepartmentController - getDepartmentList() finished: {}", txnId, gson.toJson(response)));
			
		} catch (Exception e) {
			log.error(encode("{} DepartmentController - getDepartmentList() has an error: {}", txnId, e.toString()));
			
		}
		return ResponseEntity.ok(response);
	}
	
	
	@RequestMapping(path = "/department",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DepartmentListByNameResponse> getDepartmentListByName(@RequestParam("name") String name ){
		String txnId = UUID.randomUUID().toString();
		
		log.info(encode("{} DepartmentController - getDepartmentListByName() started. ", txnId));
		DepartmentListByNameResponse response=new DepartmentListByNameResponse();
		try {
			response.setDepartmentsDtoList(departmentService.findByName(txnId,name));
			log.info(encode("{} DepartmentController - getDepartmentListByName() finished: {}", txnId, gson.toJson(response)));
			
		} catch (Exception e) {
			log.error(encode("{} DepartmentController - getDepartmentListByName() has an error: {}", txnId, e.toString()));
			
		}
		return ResponseEntity.ok(response);
	}
	
	
	@RequestMapping(path = "/department/{id}",method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DeleteDepartmentByIdResponse> deleteDepartmentById(@PathVariable("id") String id ){
		String txnId = UUID.randomUUID().toString();
		
		log.info(encode("{} DepartmentController - deleteDepartmentById() started. `", txnId));
		DeleteDepartmentByIdResponse response=new DeleteDepartmentByIdResponse();
		try {
			Integer identity=Integer.valueOf(id);//check
			response.setResponse(departmentService.deleteById(txnId,identity));
			log.info(encode("{} DepartmentController - deleteDepartmentById() finished: {}", txnId, gson.toJson(response)));
			
		} catch (Exception e) {
			log.error(encode("{} DepartmentController - deleteDepartmentById() has an error: {}", txnId, e.toString()));
			
		}
		return ResponseEntity.ok(response);
	}
	
}
