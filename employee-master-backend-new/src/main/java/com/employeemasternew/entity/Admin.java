package com.employeemasternew.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Admin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String username;
	private String email;
	private String password;
	private boolean isVerified;
	private boolean isAdmin;
	private String emailVerificationToken;
	@OneToMany
	private List<AdminActivity> activities;
	
	@Override
	public String toString() {
		return "Admin [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password
				+ ", isVerified=" + isVerified + ", isAdmin=" + isAdmin + ", emailVerificationToken="
				+ emailVerificationToken + ", activities=" + activities + "]";
	}

	public Admin() {
	}

	public Admin(long id, String username, String email, String password, boolean isVerified, boolean isAdmin,
			String emailVerificationToken, List<AdminActivity> activities) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.isVerified = isVerified;
		this.isAdmin = isAdmin;
		this.emailVerificationToken = emailVerificationToken;
		this.activities = activities;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getEmailVerificationToken() {
		return emailVerificationToken;
	}

	public void setEmailVerificationToken(String emailVerificationToken) {
		this.emailVerificationToken = emailVerificationToken;
	}

	public List<AdminActivity> getActivities() {
		return activities;
	}

	public void setActivities(List<AdminActivity> activities) {
		this.activities = activities;
	}
	
}
