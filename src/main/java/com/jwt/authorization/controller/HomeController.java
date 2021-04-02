package com.jwt.authorization.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {
	
	@RequestMapping(value="/all", method=RequestMethod.GET)
	public String allAccess() {
		return "This page is a public content.";
	}

	@RequestMapping(value="/user", method=RequestMethod.GET)
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public String userAccess() {
		return "This page is accessible for both user and admin.";
	}
	
	@RequestMapping(value="/admin", method=RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "This page is accessible to admin only.";
	}
}
