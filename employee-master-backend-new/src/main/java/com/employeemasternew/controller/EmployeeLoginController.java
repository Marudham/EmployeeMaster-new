package com.employeemasternew.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employeemasternew.entity.ApiResponse;
import com.employeemasternew.entity.Employee;
import com.employeemasternew.entity.EmployeeLogin;
import com.employeemasternew.service.AdminService;
import com.employeemasternew.service.EmailService;
import com.employeemasternew.service.EmployeeLoginService;
import com.employeemasternew.service.EmployeeService;
import com.employeemasternew.service.TokenService;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/ems/controller")
public class EmployeeLoginController {

	@Autowired
	EmployeeLoginService employeeLoginService;
	
	@Autowired
	TokenService tokenService;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	AdminService adminService;
	
	ApiResponse response;

	@PostMapping("/prepareLink")
	public ResponseEntity<ApiResponse> prepareLink(@RequestBody EmployeeLogin employeeLogin) {
		response = new ApiResponse();
		try {
			if(employeeService.isEmployeeExist(employeeLogin.getEmail())) {
				String token = tokenService.generateUniqueToken();
				employeeLogin.setVerificationToken(token);
				employeeLoginService.addEmployeeLogin(employeeLogin);
				String subject = "Employee Login - EmployeeMaster";
				String body = "To View Employee Details, "
						+ "<br><a href=\"http://localhost:3000/verifyEmployee/" + employeeLogin.getVerificationToken() 
			            + "/" + employeeLogin.getId() + "\">Click Here.</a>";
				emailService.sendEmail(employeeLogin.getEmail(), subject, body);
				response.setStatus("success");
				return ResponseEntity.ok(response);
			}else {
				response.setStatus("email-not-exist");
				response.setMessage("Entered Email does not exist in Employee Records");
				return ResponseEntity.badRequest().body(response);
			}
		}catch(Exception e) {
			e.printStackTrace();
			response.setStatus("error");
			response.setMessage("Unexpected error occured while preparing link, Please try again");
			return ResponseEntity.internalServerError().body(response);
		}
	}
	
	@GetMapping("/verifyEmployee")
	public ResponseEntity<ApiResponse> verifyEmployee(@RequestParam String token, @RequestParam Long id) {
		response = new ApiResponse();
		try {
			if(employeeLoginService.verifyToken(token,id)) {
				Employee employee = employeeService.getEmployeeByEmail(employeeLoginService.getEmployeeLogin(id).getEmail());
				response.setStatus("success");
				response.setEmployee(employee);
				return ResponseEntity.ok(response);
			}else {
				response.setStatus("cannot-verify");
				response.setMessage("Cannot verify the Link");
				return ResponseEntity.badRequest().body(response);
			}
		}catch(Exception e) {
			e.printStackTrace();
			response.setStatus("error");
			response.setMessage("Unexpected error occured while verifying link, Please try again");
			return ResponseEntity.internalServerError().body(response);
		}
	}
	
}
