package com.employeemasternew.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employeemasternew.entity.EmployeeRequest;

public interface EmployeeRequestRepository extends JpaRepository<EmployeeRequest, Long>{

	List<EmployeeRequest> findByEmployeeId(Long id);

}
