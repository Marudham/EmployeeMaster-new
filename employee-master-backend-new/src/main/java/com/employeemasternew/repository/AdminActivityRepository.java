package com.employeemasternew.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employeemasternew.entity.AdminActivity;

public interface AdminActivityRepository extends JpaRepository<AdminActivity, Long>{

	List<AdminActivity> findByAdminId(Long id);

	void deleteByEmployeeId(long id);

}
