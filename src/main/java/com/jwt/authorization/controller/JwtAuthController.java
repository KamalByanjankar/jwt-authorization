package com.jwt.authorization.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.authorization.model.ERole;
import com.jwt.authorization.model.Role;
import com.jwt.authorization.model.User;
import com.jwt.authorization.payload.request.LoginRequest;
import com.jwt.authorization.payload.request.SignupRequest;
import com.jwt.authorization.payload.response.JwtResponse;
import com.jwt.authorization.payload.response.MessageResponse;
import com.jwt.authorization.repository.RoleRepository;
import com.jwt.authorization.repository.UserRepository;
import com.jwt.authorization.service.UserDetailsServiceImpl;
import com.jwt.authorization.utility.JwtUtils;

@RestController
@RequestMapping("/api/auth")
public class JwtAuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	

	@RequestMapping(value="/signin", method=RequestMethod.POST)
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws Exception{
		
		try{
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
		}
		catch(UsernameNotFoundException e){
			e.printStackTrace();
			throw new Exception("Bad Credentials");
		}
		catch(BadCredentialsException e){
			e.printStackTrace();
			throw new Exception("Bad Credentials");
		}
				
		
		UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(loginRequest.getUserName());
		
		String jwtToken = jwtUtils.generateToken(userDetails);
		System.out.println("JWT TOKEN:" + jwtToken);


		return ResponseEntity.ok(new JwtResponse(jwtToken));
	}
	
	@RequestMapping(value="/signup", method = RequestMethod.POST)
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) throws Exception{
		if(userRepository.existsByUserName(signupRequest.getUserName())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username not available"));
		}
		
		if (userRepository.existsByEmail(signupRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email already exists"));
		}
		
		
		//Create new User account
		User user = new User(signupRequest.getUserName(), signupRequest.getEmail(), passwordEncoder.encode(signupRequest.getPassword()));
		Set<String> strRoles = signupRequest.getRole();
		Set<Role> roles = new HashSet<>();
		
		if(strRoles == null) {
			Role userRole = roleRepository.findByRoleName(ERole.ROLE_USER);
			roles.add(userRole);
			
		}
		
		else {
			strRoles.forEach(role -> {
				switch(role) {
				case "admin":
					Role adminRole = roleRepository.findByRoleName(ERole.ROLE_ADMIN);
					roles.add(adminRole);
					
					break;
					
					
				default: 
					Role userRole = roleRepository.findByRoleName(ERole.ROLE_USER);
					roles.add(userRole);

				}
			});
		}
		
		user.setRoles(roles);
		userRepository.save(user);
		
		return ResponseEntity.ok(new MessageResponse("User successfully registered"));
	}
}
