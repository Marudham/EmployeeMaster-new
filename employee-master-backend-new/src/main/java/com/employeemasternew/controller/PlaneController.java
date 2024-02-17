package com.employeemasternew.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.employeemasternew.service.AdminService;
import com.employeemasternew.service.EmployeeService;

import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("ems/controller")
public class PlaneController {

	@Autowired
	AdminService adminService;
	
	@Autowired
	EmployeeService employeeService;
	
	@GetMapping("/verify")
	public String verifyToken(@RequestParam String token, @RequestParam Long id, Model model) {
		try {
			if(adminService.verifyToken(id,token)) {
				model.addAttribute("message", "success");
				return "verification"; 
			}else {
				model.addAttribute("message", "cannot-verify");
				return "verification";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "error");
			return "verification";
		}
	}
	
	@GetMapping("/empVerify")
	public String empVerify(@RequestParam String token, @RequestParam Long id, Model model) {
		try {
			if(employeeService.verifyToken(id,token)) {
				model.addAttribute("message", "success");
				return "verification"; 
			}else {
				model.addAttribute("message", "cannot-verify");
				return "verification";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "error");
			return "verification";
		}
	}
}
