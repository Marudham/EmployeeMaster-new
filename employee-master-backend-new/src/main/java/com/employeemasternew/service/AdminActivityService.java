package com.employeemasternew.service;

import java.util.List;

import com.employeemasternew.entity.AdminActivity;

public interface AdminActivityService {

	void addActivity(AdminActivity adminActivity);

	List<AdminActivity> fetchActivityByAdminId(Long id);

	List<AdminActivity> fetchAllActivity();

}
