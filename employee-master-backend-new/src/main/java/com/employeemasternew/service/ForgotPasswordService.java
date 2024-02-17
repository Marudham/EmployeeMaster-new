package com.employeemasternew.service;

import com.employeemasternew.entity.ForgotPassword;

public interface ForgotPasswordService {

	void save(ForgotPassword forgotPassword);

	boolean verifyToken(String token, Long id);

	ForgotPassword getById(Long id);

	ForgotPassword getByEmail(String email);

	
}
