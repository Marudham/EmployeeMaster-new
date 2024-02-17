package com.employeemasternew.service;

import java.util.List;

import com.employeemasternew.entity.Employee;

public interface EmployeeService {

	boolean isEmployeeExist(String email);

	List<Employee> fetchAllEmployee();

	void addEmployee(Employee employee);

	Employee getEmployeeById(long id);

	void deleteEmployee(long id);

	Employee getEmployeeByEmail(String email);

	List<Employee> fetchAllEmployeeByAdminId(long id);

	List<Employee> filterEmployees(String department, String position) throws Exception;

	boolean isPhoneNoExist(String phoneNo);

	void updateEmployee(Employee employee);

	void registerEmployee(Employee employee) throws Exception ;

	boolean verifyToken(Long id, String token);

	boolean isValidEmployee(String email, String password);

	List<Employee> fetchAllEmployeeByApprove();

	List<Employee> fetchAllRequestEmployee();

}
