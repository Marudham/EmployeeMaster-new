package com.employeemasternew.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class EmployeeRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Employee employee;
	private String field;
	private String value;
	private boolean isApproved;
	private boolean isExecuted;
	@ManyToOne
	private Admin approvedBy;
	@ManyToOne
	private Admin executedBy;

	@Override
	public String toString() {
		return "EmployeeRequest [id=" + id + ", employee=" + employee + ", field=" + field + ", value=" + value
				+ ", isApproved=" + isApproved + ", isExecuted=" + isExecuted + ", approvedBy=" + approvedBy
				+ ", executedBy=" + executedBy + "]";
	}

	public EmployeeRequest() {
		
	}
	
	public EmployeeRequest(Long id, Employee employee, String field, String value, boolean isApproved,
			boolean isExecuted, Admin approvedBy, Admin executedBy) {
		super();
		this.id = id;
		this.employee = employee;
		this.field = field;
		this.value = value;
		this.isApproved = isApproved;
		this.isExecuted = isExecuted;
		this.approvedBy = approvedBy;
		this.executedBy = executedBy;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Admin getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(Admin approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Admin getExecutedBy() {
		return executedBy;
	}

	public void setExecutedBy(Admin executedBy) {
		this.executedBy = executedBy;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	public boolean isExecuted() {
		return isExecuted;
	}

	public void setExecuted(boolean isExecuted) {
		this.isExecuted = isExecuted;
	}
	
}
