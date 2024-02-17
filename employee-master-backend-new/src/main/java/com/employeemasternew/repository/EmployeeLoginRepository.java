package com.employeemasternew.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employeemasternew.entity.EmployeeLogin;

public interface EmployeeLoginRepository extends JpaRepository<EmployeeLogin, Long>{

	EmployeeLogin findByEmail(String email);

}
