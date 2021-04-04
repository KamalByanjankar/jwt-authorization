package com.jwt.authorization.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jwt.authorization.model.User;

public class UserDetailsImpl implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String userName;
	
	@JsonIgnore
	private String password;
	private String email;
	private Collection<? extends GrantedAuthority> authorities;
	
	
	
	public UserDetailsImpl() {
		
	}


	public UserDetailsImpl(Long id, String userName, String password, String email,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.authorities = authorities;
	}


	public static UserDetailsImpl build(User user) {
		 return null;
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
