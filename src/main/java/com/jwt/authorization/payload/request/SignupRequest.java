package com.jwt.authorization.payload.request;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class SignupRequest {
	
	@NotBlank
	private String userName;
	
	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	private String password;
	
	
	private Set<String> role;
	
	

	public SignupRequest() {
		
	}

	public SignupRequest(@NotBlank String userName, @NotBlank @Email String email, @NotBlank String password,
			Set<String> role) {
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public Set<String> getRole() {
		return role;
	}

	public void setRole(Set<String> role) {
		this.role = role;
	}

	
}
