package com.employeemasternew.entity;

public class RegisterData {

	private String username;
	private String email;
	private String password;
	
	@Override
	public String toString() {
		return "RegisterData [username=" + username + ", email=" + email + ", password=" + password + "]";
	}

	public RegisterData() {
		
	}

	public RegisterData(String username, String email, String password) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
