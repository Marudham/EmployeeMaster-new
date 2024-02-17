package com.employeemasternew.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employeemasternew.entity.Admin;
import com.employeemasternew.entity.ApiResponse;
import com.employeemasternew.entity.Employee;
import com.employeemasternew.entity.LoginData;
import com.employeemasternew.service.AdminActivityService;
import com.employeemasternew.service.AdminService;
import com.employeemasternew.service.EmailService;
import com.employeemasternew.service.EmployeeService;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/ems/controller")
public class AdminController {

	@Autowired
	AdminService adminService;

	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	AdminActivityService adminActivityService;

	@Autowired
	EmailService emailService;

	ApiResponse response = new ApiResponse();

	@PostMapping("/login")
	public ResponseEntity<ApiResponse> login(@RequestBody LoginData data) {
		response = new ApiResponse();
		try {
			String email = data.getEmail();
			String password = data.getPassword();
			if(adminService.isAdminExist(email)) {
				if(adminService.isValidAdmin(email,password)) {
					Admin admin = adminService.getAdmin(email);
					if(admin.isVerified()) {
						if(admin.isAdmin()) {
							response.setStatus("success");
							response.setAdmin(admin);
							return ResponseEntity.ok(response);
						}else {
							response.setStatus("not-approved");
							response.setMessage("Entered Email is not Approved By SUPER_ADMIN");
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

	@PostMapping("/register")
	public ResponseEntity<ApiResponse> register(@RequestBody Admin admin) {
		response = new ApiResponse();
		try {
			if(!admin.getEmail().equals("marudhamts@gmail.com")) {
				if(!employeeService.isEmployeeExist(admin.getEmail())) {
					if(!adminService.isAdminExist(admin.getEmail())) {
						adminService.addAdmin(admin);
						response.setStatus("success");
						response.setMessage("Registered Successfully");
						return ResponseEntity.ok(response);
					}else {
						response.setStatus("already-exist");
						response.setMessage("Entered Email already Registered");
						return ResponseEntity.badRequest().body(response);
					}
				}else {
					response.setStatus("already-exist");
					response.setMessage("Entered Email already exist in employee records");
					return ResponseEntity.badRequest().body(response);				
				}
			}else {
				response.setStatus("email-not-allowed");
				response.setMessage("Cannot register with this Email id");
				return ResponseEntity.badRequest().body(response);		
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus("error");
			response.setMessage("Unexpected Error Occured While Registering, Please try Again");
			return ResponseEntity.internalServerError().body(response);
		}

	}

	@GetMapping("/fetchEmployees")
	public ResponseEntity<ApiResponse> fetchEmployees(){
		response = new ApiResponse();
		try {
			List<Employee> employeeList = employeeService.fetchAllEmployeeByApprove();
			response.setStatus("success");
			response.setEmployeeList(employeeList);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus("error");
			response.setMessage("Unexpected Error Occured While Retrieving Employee details, Please try Again");
			return ResponseEntity.internalServerError().body(response);
		}
	}
	
	@GetMapping("/fetchRequestEmployees")
	public ResponseEntity<ApiResponse> fetchRequestEmployees(){
		response = new ApiResponse();
		try {
			List<Employee> employeeList = employeeService.fetchAllRequestEmployee();
			response.setStatus("success");
			response.setEmployeeList(employeeList);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus("error");
			response.setMessage("Unexpected Error Occured While Retrieving Employee details, Please try Again");
			return ResponseEntity.internalServerError().body(response);
		}
	}

	@GetMapping("/getAdminId")
	public ResponseEntity<Long> getAdminId(@RequestParam String email){
		response = new ApiResponse();
		try {
			return ResponseEntity.ok(adminService.getAdmin(email).getId());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(-1l);
		}
	}

}
