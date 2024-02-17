package com.employeemasternew.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employeemasternew.entity.AdminActivity;
import com.employeemasternew.entity.ApiResponse;
import com.employeemasternew.entity.Employee;
import com.employeemasternew.entity.LoginData;
import com.employeemasternew.service.AdminActivityService;
import com.employeemasternew.service.AdminService;
import com.employeemasternew.service.EmployeeService;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/ems/controller")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	AdminActivityService adminActivityService;

	ApiResponse response;

	AdminActivity adminActivity;
	
	@PostMapping("/empRegister")
	public ResponseEntity<ApiResponse> empRegister(@RequestBody Employee employee) {
		response = new ApiResponse();
		try {
			if(!employee.getEmail().equals("marudhamts@gmail.com")) {
				if(!adminService.isAdminExist(employee.getEmail())) {
					if(!employeeService.isEmployeeExist(employee.getEmail())) {
						if(!employeeService.isPhoneNoExist(employee.getPhoneNo())) {
							employeeService.registerEmployee(employee);
							response.setStatus("success");
							return ResponseEntity.ok(response);
						}else {
							response.setStatus("phoneNo-exist");
							response.setMessage("Entered Employee Phone No already exist");
							return ResponseEntity.badRequest().body(response);
						}
					}else {
						response.setStatus("email-exist");
						response.setMessage("Entered Employee Email already exist");
						return ResponseEntity.badRequest().body(response);
					}
				}else {
					response.setStatus("email-exist");
					response.setMessage("Entered Employee Email already exist in Admin records");
					return ResponseEntity.badRequest().body(response);
				}
			}else {
				response.setStatus("email-not-allowed");
				response.setMessage("Cannot register with this Email id");
				return ResponseEntity.badRequest().body(response);				
			}

		}catch(Exception e) {
			e.printStackTrace();
			response.setStatus("error");
			response.setMessage("Unexpected error occured while Adding employee, Please try again");
			return ResponseEntity.internalServerError().body(response);
		}
	}
	
	@PostMapping("/empLogin")
	public ResponseEntity<ApiResponse> login(@RequestBody LoginData data) {
		response = new ApiResponse();
		try {
			String email = data.getEmail();
			String password = data.getPassword();
			if(employeeService.isEmployeeExist(email)) {
				if(employeeService.isValidEmployee(email,password)) {
					Employee employee = employeeService.getEmployeeByEmail(email);
					if(employee.isVerified()) {
						if(employee.getIsApproved()) {
							response.setStatus("success");
							response.setEmployee(employee);
							return ResponseEntity.ok(response);
						}else {
							response.setStatus("not-approved");
							response.setMessage("Entered Email is not Approved By Admin");
							return ResponseEntity.badRequest().body(response);
						}
					}else {
						response.setStatus("not-verified");
						response.setMessage("Entered Email is not verified");
						return ResponseEntity.badRequest().body(response);
					}
				}else {
					response.setStatus("incorrect-password");
					response.setMessage("Entered Password is Incorrect");
					return ResponseEntity.badRequest().body(response);
				}
			}else {
				response.setStatus("email-not-match");
				response.setMessage("Entered Email Does not exist");
				return ResponseEntity.badRequest().body(response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus("error");
			response.setMessage("Unexpected Error Occured While Verifying Login, Please try Again");
			return ResponseEntity.internalServerError().body(response);
		}
	}
	
	@PostMapping("/addEmployee")
	public ResponseEntity<ApiResponse> addEmployee(@RequestBody Employee employee, @RequestParam Long id) {
		response = new ApiResponse();
		try {
			adminActivity = new AdminActivity();
			if(!employeeService.isEmployeeExist(employee.getEmail())) {
				if(!employeeService.isPhoneNoExist(employee.getPhoneNo())) {
					employee.setAddedByAdmin(adminService.getAdminById(id));
					employee.setIsApproved(true);
					employeeService.addEmployee(employee);
					adminActivity.setActivity("Add");
					adminActivity.setChangeMade("Added Employee : " + employee.getFirstName() + " " + employee.getSecondName());
					adminActivity.setEmployee(employee);
					adminActivity.setAdmin(adminService.getAdminById(id));
					adminActivity.setTimestamp(LocalDateTime.now());
					adminActivityService.addActivity(adminActivity);
					response.setStatus("success");
					return ResponseEntity.ok(response);
				}else {
					response.setStatus("phoneNo-exist");
					response.setMessage("Entered Employee Phone No already exist");
					return ResponseEntity.badRequest().body(response);
				}
			}else {
				response.setStatus("email-exist");
				response.setMessage("Entered Employee Email already exist");
				return ResponseEntity.badRequest().body(response);
			}

		}catch(Exception e) {
			e.printStackTrace();
			response.setStatus("error");
			response.setMessage("Unexpected error occured while Adding employee, Please try again");
			return ResponseEntity.internalServerError().body(response);
		}
	}

	@GetMapping("/employeeDetails")
	public ResponseEntity<ApiResponse> employeeDetails(@RequestParam Long id) {
		response = new ApiResponse();
		try {
			Employee employee = employeeService.getEmployeeById(id);
			response.setStatus("success");
			response.setEmployee(employee);
			return ResponseEntity.ok(response);
		}catch(Exception e) {
			e.printStackTrace();
			response.setStatus("error");
			response.setMessage("Unexpected error occured while retrieving employee details");
			return ResponseEntity.internalServerError().body(response);
		}
	}
	
	@GetMapping("/approveEmployee")
	public ResponseEntity<ApiResponse> approveEmployee(@RequestParam Long id, @RequestParam Long adminId) {
		response = new ApiResponse();
		try {
			adminActivity = new AdminActivity();
			Employee employee = employeeService.getEmployeeById(id);
			if(employee.isVerified()) {
				employee.setIsApproved(true);
				employee.setApprovedByAdmin(adminService.getAdminById(adminId));
				employeeService.updateEmployee(employee);
				adminActivity.setActivity("Approve");
				adminActivity.setChangeMade("Approved Registered Employee: " + employee.getFirstName() + " " + employee.getSecondName());
				adminActivity.setTimestamp(LocalDateTime.now());
				adminActivity.setEmployee(employee);
				adminActivity.setAdmin(adminService.getAdminById(adminId));
				adminActivityService.addActivity(adminActivity);
				response.setStatus("success");
				return ResponseEntity.ok(response);
			}else {
				response.setStatus("not-verified");
				response.setMessage("Cannot Approve Employee who are not verified");
				return ResponseEntity.badRequest().body(response);				
			}
		}catch(Exception e) {
			e.printStackTrace();
			response.setStatus("error");
			response.setMessage("Unexpected error occured while retrieving employee details");
			return ResponseEntity.internalServerError().body(response);
		}
	}

	@PutMapping("/updateEmployee")
	public ResponseEntity<ApiResponse> updateEmployee(@RequestBody Employee employee, @RequestParam String changeMade, @RequestParam long id) {
		response = new ApiResponse();
		try {
			employeeService.updateEmployee(employee);
		
			adminActivity = new AdminActivity();
			adminActivity.setAdmin(adminService.getAdminById(id));
			adminActivity.setActivity("Update");
			adminActivity.setTimestamp(LocalDateTime.now());
			if(changeMade != "") {
				adminActivity.setChangeMade("Updated " + changeMade + " of : " + employee.getFirstName() + " " + employee.getSecondName());
			}else {
				adminActivity.setChangeMade("Updated Employee : " + employee.getFirstName() + " " + employee.getSecondName());
			}
			adminActivity.setEmployee(employee);
			adminActivityService.addActivity(adminActivity);
			response.setStatus("success");
			return ResponseEntity.ok(response);
		}catch(Exception e) {
			e.printStackTrace();
			response.setStatus("error");
			response.setMessage("Unexpected error occured while updating employee details");
			return ResponseEntity.internalServerError().body(response);
		}
	}

	@GetMapping("/deleteEmployee")
	public ResponseEntity<ApiResponse> deleteEmployee(@RequestParam Long id, @RequestParam long adminId) {
		response = new ApiResponse();
		try {
			Employee employee = employeeService.getEmployeeById(id);
			employeeService.deleteEmployee(id);
			adminActivity = new AdminActivity();
			adminActivity.setAdmin(adminService.getAdminById(adminId));
			adminActivity.setActivity("Delete");
			adminActivity.setTimestamp(LocalDateTime.now());
			adminActivity.setChangeMade("Deleted Employee : " + employee.getFirstName() + " " + employee.getSecondName());
			adminActivityService.addActivity(adminActivity);
			response.setStatus("success");
			return ResponseEntity.ok(response);
		} catch(Exception e) {
			e.printStackTrace();
			response.setStatus("error");
			response.setMessage("Unexpected error occured while  Deleting Employee");
			return ResponseEntity.internalServerError().body(response);
		}
	}

	@GetMapping("/applyFilter")
	public ResponseEntity<ApiResponse> applyFilter(@RequestParam("filterBasedOn") String filterBasedOn, 
			@RequestParam("filterValue") String filterValue) {
		response = new ApiResponse();
		try {
			List<Employee> filteredEmployees = employeeService.filterEmployees(filterBasedOn, filterValue);
			response.setStatus("success");
			response.setEmployeeList(filteredEmployees);
			return ResponseEntity.ok(response);
		}catch (Exception e) {
			e.printStackTrace();
			response.setStatus("error");
			response.setMessage("Unexpected error occured while Applying filter on Employees");
			return ResponseEntity.internalServerError().body(response);
		}
	}
	
	@GetMapping("/fetchEmployee")
	public ResponseEntity<ApiResponse> fetchEmployee(@RequestParam Long id) {
		response = new ApiResponse();
		try {
			Employee employee = employeeService.getEmployeeById(id);
			response.setStatus("success");
			response.setEmployee(employee);
			return ResponseEntity.ok(response);
		}catch (Exception e) {
			e.printStackTrace();
			response.setStatus("error");
			response.setMessage("Unexpected error occured while fetching Employee details");
			return ResponseEntity.internalServerError().body(response);
		}
	}

}
