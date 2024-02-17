package com.employeemasternew.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employeemasternew.entity.SuperAdmin;

public interface SuperAdminRepository extends JpaRepository<SuperAdmin, Integer>{

	SuperAdmin findByEmail(String email);

}
