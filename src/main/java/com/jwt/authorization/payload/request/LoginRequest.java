package com.jwt.authorization.payload.request;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
	
	@NotBlank
	private String userName;
	
	@NotBlank
	private String password;

	public LoginRequest() {

	}

	public LoginRequest(@NotBlank String userName, @NotBlank String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
}
