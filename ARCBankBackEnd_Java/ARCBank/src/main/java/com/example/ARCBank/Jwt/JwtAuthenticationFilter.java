package com.example.ARCBank.Jwt;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer.UserDetailsBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.ARCBank.Models.User;
import com.example.ARCBank.Repositories.UserRepository;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtService jwtService;
	
	//@Autowired
	//private UserDetailsService userDetailsService;
	
	@Autowired
	private UserRepository repo;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//Get Bearer Token from Header
		 final String authHeader = request.getHeader("Authorization");

		 if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			 filterChain.doFilter(request, response);
			 return;
	     }
		 
		 //Get jwt Token from Header
		 final String jwtToken = authHeader.substring(7);
		 
		 try {
			 
			if(StringUtils.hasText(jwtToken) && jwtService.validate(jwtToken)) {
				 
				 //Get Username from Token
				 final String username = jwtService.getUsernameFromToken(jwtToken);
				 
				 //Get User from Repo 
				 User user = repo.findByUsername(username).get();
				 Set<GrantedAuthority> set = new HashSet<>();
				 set.add(new SimpleGrantedAuthority("ROLE_"+user.getRole().toUpperCase()));
				 
				 //By Using user details service :
				 //UserDetails user = userDetailsService.loadUserByUsername(username);
				 
				 //Add this User details in Authentication Token
			     UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
			                 user.getUsername(),
			                 null,
			                 set
			         );

			     authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			     
			     //Set Token into Security Context
			     SecurityContextHolder.getContext().setAuthentication(authToken);    
			 }
		} catch (UsernameNotFoundException e) {
			System.out.println("Username not found Exception");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
         
		filterChain.doFilter(request, response);
	}

}
