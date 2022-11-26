package com.avinty.hr.repo;

import com.avinty.hr.model.entity.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employees,Integer> {
	List<Employees> findAll();
	Employees save(Employees employees);
	
}
