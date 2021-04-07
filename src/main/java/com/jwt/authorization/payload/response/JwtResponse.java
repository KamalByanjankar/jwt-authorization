package com.jwt.authorization.payload.response;

import java.util.List;

public class JwtResponse {

	private String jwtToken;
	private Long id;
	private String username;
	private List<String> roles;
	
	public JwtResponse() {

	}


	public JwtResponse(String jwtToken, Long id, String username, List<String> roles) {
		this.jwtToken = jwtToken;
		this.id = id;
		this.username = username;
		this.roles = roles;
	}


	public String getJwtToken() {
		return jwtToken;
	}


	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public List<String> getRoles() {
		return roles;
	}


	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	
}
