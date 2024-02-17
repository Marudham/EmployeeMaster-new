package com.employeemasternew.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employeemasternew.entity.Employee;
import com.employeemasternew.repository.AdminActivityRepository;
import com.employeemasternew.repository.EmployeeRepository;

import jakarta.transaction.Transactional;

@Service
public class EmployeeServiceImplementation implements EmployeeService{

	@Autowired
	EmployeeRepository employeeRepo;
	
	@Autowired
	AdminActivityRepository adminActivityRepo;
	
	@Autowired
	TokenService tokenService;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	PasswordEncryptionService passwordEncryptionService;
	
	@Override
	public void registerEmployee(Employee employee) throws Exception {
		String token = tokenService.generateUniqueToken();
		employee.setEmailVerificationToken(token);
		employee.setPassword(passwordEncryptionService.encryptPassword(employee.getPassword()));
		employeeRepo.save(employee);
		String subject = "Account Verification - EmployeeMaster";
		String body = "To verify your account: "
				+ "<br><a href=\"http://localhost:8080/ems/controller/empVerify?token=" + employee.getEmailVerificationToken() 
				+ "&id=" + employee.getId() + "\">Click Here.</a>";
		emailService.sendEmail(employee.getEmail(), subject, body);
	}
	
	@Override
	public boolean isValidEmployee(String email, String password) {
		String passwordDB = employeeRepo.findByEmail(email).getPassword();
		
		if(passwordEncryptionService.checkPassword(password, passwordDB)) {
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public boolean verifyToken(Long id, String token) {
		Employee employee = employeeRepo.findById(id).get();
		
		if(employee != null) {
			if(employee.getEmailVerificationToken().equals(token)) {
				employee.setVerified(true);
				employeeRepo.save(employee);
				return true;
			}else {
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean isEmployeeExist(String email) {
		if(employeeRepo.findByEmail(email) == null) {
			return false;
		}else {
			return true;
		}
	}

	@Override
	public List<Employee> fetchAllEmployee() {
		return employeeRepo.findAll();
	}

	@Override
	public void addEmployee(Employee employee) {
		employeeRepo.save(employee);	
	}

	@Override
	public Employee getEmployeeById(long id) {
		return employeeRepo.findById(id).get();
	}

	@Override
	@Transactional
	public void deleteEmployee(long id) {
		adminActivityRepo.deleteByEmployeeId(id);
		employeeRepo.deleteById(id);
	}

	@Override
	public Employee getEmployeeByEmail(String email) {
		return employeeRepo.findByEmail(email);
	}

	@Override
	public List<Employee> fetchAllEmployeeByAdminId(long id) {

		return employeeRepo.findByAddedByAdminId(id);
	}

	@Override
	public List<Employee> filterEmployees(String filterBasedOn, String filterValue) throws Exception{
		List<Employee> employees = new ArrayList<>();
		List<Employee> filteredEmployees = new ArrayList<>();
		switch(filterBasedOn) {
		case "firstName" :	employees = employeeRepo.findAllByFirstName(filterValue);
		break;
		case "secondName" :	employees = employeeRepo.findAllBySecondName(filterValue);
		break;
		case "email" :	employees = employeeRepo.findAllByEmail(filterValue);
		break;
		case "phoneNo" :	employees = employeeRepo.findAllByPhoneNo(filterValue);
		break;
		case "dateOfBirth" :	employees = employeeRepo.findAllByDateOfBirth(filterValue);
		break;
		case "address" :	employees = employeeRepo.findAllByAddress(filterValue);
		break;
		case "gender" :	employees = employeeRepo.findAllByGender(filterValue);
		break;
		case "education" :	employees = employeeRepo.findAllByEducation(filterValue);
		break;
		case "percentage10" :	employees = employeeRepo.findAllByPercentage10(filterValue);
		break;
		case "percentage12" :	employees = employeeRepo.findAllByPercentage12(filterValue);
		break;
		case "percentageDeg" :	employees = employeeRepo.findAllByPercentageDeg(filterValue);
		break;
		case "department" :	employees = employeeRepo.findAllByDepartment(filterValue);
		break;
		case "joinDate" :	employees = employeeRepo.findAllByJoinDate(filterValue);
		break;
		case "position" :	employees = employeeRepo.findAllByPosition(filterValue);
		break;
		case "salary" :	employees = employeeRepo.findAllBySalary(filterValue);
		break;
		case "supervisor" :	employees = employeeRepo.findAllBySupervisor(filterValue);
		break;
		case "project" :	employees = employeeRepo.findAllByProject(filterValue);
		break;
		case "status" :	employees = employeeRepo.findAllByStatus(filterValue);
		break;
		default : return new ArrayList<>();
		}
		for(Employee employee : employees) {
			if(employee.getIsApproved()) {
				filteredEmployees.add(employee);
			}
		}
		return filteredEmployees;
		
	}

	@Override
	public boolean isPhoneNoExist(String phoneNo) {
		if(employeeRepo.findByPhoneNo(phoneNo) == null) {
			return false;
		}else{
			return true;
		}
	}

	@Override
	public void updateEmployee(Employee employee) {
		employeeRepo.save(employee);
	}

	@Override
	public List<Employee> fetchAllEmployeeByApprove() {
		return employeeRepo.findAllByIsApproved(true);
	}

	@Override
	public List<Employee> fetchAllRequestEmployee() {
		return employeeRepo.findAllByIsApproved(false);
	}

}
