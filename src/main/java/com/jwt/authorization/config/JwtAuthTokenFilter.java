package com.jwt.authorization.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jwt.authorization.service.UserDetailsServiceImpl;
import com.jwt.authorization.utility.JwtUtils;

@Component
public class JwtAuthTokenFilter extends OncePerRequestFilter{
	
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthTokenFilter.class);
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String jwtTokenHeader = request.getHeader("Authorization");
		String username = null;
		String jwtToken = null;
		
		if(jwtTokenHeader != null && jwtTokenHeader.startsWith("Bearer ")) {
			jwtToken = jwtTokenHeader.substring(7, jwtTokenHeader.length());
			
			try {
				username = jwtUtils.extractUsername(jwtToken);
			}
			catch(Exception e) {
				e.printStackTrace();
				logger.error("Cannot set user authentication: {}", e);
			}
			
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			
			if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			else {
				System.out.println("Token is not valid");
			}
		}
		
		filterChain.doFilter(request, response);
	}
}