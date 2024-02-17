package com.employeemasternew.entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class AdminActivity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String activity;
	private String changeMade;
	private LocalDateTime timestamp;
	@ManyToOne(cascade = CascadeType.ALL)
	private Employee employee;
	@ManyToOne
	private Admin admin;
	
	@Override
	public String toString() {
		return "AdminActivity [id=" + id + ", activity=" + activity + ", changeMade=" + changeMade + ", timestamp="
				+ timestamp + ", employee=" + employee + ", admin=" + admin + "]";
	}

	public AdminActivity() {
	}

	public AdminActivity(Long id, String activity, String changeMade, LocalDateTime timestamp, Employee employee,
			Admin admin) {
		super();
		this.id = id;
		this.activity = activity;
		this.changeMade = changeMade;
		this.timestamp = timestamp;
		this.employee = employee;
		this.admin = admin;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getChangeMade() {
		return changeMade;
	}

	public void setChangeMade(String changeMade) {
		this.changeMade = changeMade;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	
}
