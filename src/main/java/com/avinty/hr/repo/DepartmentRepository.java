package com.avinty.hr.repo;

import com.avinty.hr.model.entity.Departments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
//@Transactional(propagation =Propagation.REQUIRED)
public interface DepartmentRepository  extends JpaRepository<Departments,Integer> {
	List<Departments> findAll();
	List<Departments> findDepartmentsByNameContaining(String name);
	List<Departments> findDepartmentsById(Integer id);
	void deleteDepartmentsById(Integer id);
}
