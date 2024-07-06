package com.example.ARCBank.Security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.ARCBank.Models.User;
import com.example.ARCBank.Repositories.UserRepository;

@Component
public class UserNamePwdAuthentication implements AuthenticationProvider{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder encoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String uname = authentication.getName();
		String pwd = authentication.getCredentials().toString();
		
		System.out.println("Customer ID :"+uname+" ->"+pwd);
		
		User user = userRepository.findByUsername(uname).get() ;
		
		if(user!=null && encoder.matches(pwd,user.getPassword())) {
			
			Set<GrantedAuthority> set = new HashSet<>();
			set.add(new SimpleGrantedAuthority("ROLE_"+user.getRole().toUpperCase()));
			System.out.println("Success");
			
			//Generate JWT Token 
			
			return new UsernamePasswordAuthenticationToken(uname,pwd,set);
		
		}else {
			System.out.println("Invalid CREDS");
			throw new BadCredentialsException("Invalid Credentials");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
