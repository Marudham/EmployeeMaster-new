package com.employeemasternew.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employeemasternew.entity.ForgotPassword;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Long>{

	ForgotPassword findByEmail(String email);

}
