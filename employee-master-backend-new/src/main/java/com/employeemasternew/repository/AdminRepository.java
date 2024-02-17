package com.employeemasternew.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employeemasternew.entity.Admin;


public interface AdminRepository  extends JpaRepository<Admin, Long>{

	Admin findByEmail(String email);

	Admin findByIdAndEmailVerificationToken(Long id, String token);

}
