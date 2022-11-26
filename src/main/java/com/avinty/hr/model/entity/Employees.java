package com.avinty.hr.model.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Data
@Table( schema = "PUBLIC",name = "EMPLOYEES" )
public class Employees {
	
	@Basic
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String  password;
	
	@Column(name="full_name")
	private String fullName;
	
	@Column(name="dep_id" ,insertable = false, updatable = false)
	private Integer departmentId;
	
	@Column(name="created_at")
	@DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
	private Timestamp createdAt;
	
	@Column(name="created_by")
	private Integer createdBy;
	
	@Column(name="updated_at")
	@DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
	private Timestamp updatedAt;
	
	@Column(name="updated_by")
	private Integer updatedBy;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="dep_id")
	private Departments departments;
	
}
