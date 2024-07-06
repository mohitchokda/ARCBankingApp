package com.example.ARCBank.Security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.ARCBank.Repositories.UserRepository;

//@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	private UserRepository userRepository;
	
	public CustomUserDetailsService(UserRepository ur) {
		this.userRepository = ur;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    com.example.ARCBank.Models.User user = userRepository.findByUsername(username)
	    													.orElseThrow(() -> new UsernameNotFoundException("Username not found"));
	    
	    //System.out.println("UserDetailsService : "+user.getUsername());
	    
		Set<GrantedAuthority> set = new HashSet<>();
		set.add(new SimpleGrantedAuthority("ROLE_"+user.getRole().toUpperCase()));
		
		return new User(user.getUsername(),user.getPassword(),set);
	}

}
