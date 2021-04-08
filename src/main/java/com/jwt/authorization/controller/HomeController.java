package com.jwt.authorization.controller;

import java.security.Principal;
import java.util.Collection;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class HomeController {
	
	@RequestMapping(value="/all", method=RequestMethod.GET)
	public String allAccess() {
		return "This page is a public content.";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/user", method=RequestMethod.GET)
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public String userAccess(Principal principal) {
		String username = principal.getName();

		Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		
		return "Currently logged in as " + username + " and have a role of " + authorities;
	}
	
	@RequestMapping(value="/admin", method=RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "This page is accessible to admin only.";
	}
}
