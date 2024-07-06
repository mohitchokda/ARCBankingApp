package com.example.ARCBank.Services.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ARCBank.Dtos.LoginDto;
import com.example.ARCBank.Dtos.UserUpdateDto;
import com.example.ARCBank.Jwt.JwtService;
import com.example.ARCBank.Models.User;
import com.example.ARCBank.Repositories.UserRepository;
import com.example.ARCBank.Security.UserNamePwdAuthentication;
import com.example.ARCBank.Services.UserService;

import jakarta.validation.Valid;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	UserNamePwdAuthentication userPwdAuth;
	
	@Autowired
	JwtService jwtService;
	
	public UserServiceImpl() {
	}

	public String register(User user) {
		if(userRepository.findByEmail(user.getEmail()) == null) {
			user.setPassword(encoder.encode(user.getPassword()));
			user.setRole("user");
			userRepository.save(user);
			return "User Registered Successfully";
		}
		return "User Already Registered, Please Login";
	}

	@Override
	public String login(LoginDto login) {
		Authentication auth = userPwdAuth.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));

		if(auth.isAuthenticated()) {
			
			SecurityContextHolder.getContext().setAuthentication(auth);
			String token  = jwtService.generateToken(auth);
			return token;
		}
		return "Invalid Credentials";
	}
	
	public User loginGetUser(LoginDto login) {
		
		Authentication auth = userPwdAuth.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
		
		//System.out.println(auth.getName());
		
		if(auth.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(auth);
			return getUserByUsername(login.getUsername());
		}
		else throw new BadCredentialsException("Invalid Credentials, Please Retry");
	}
	
	@Override
	public User getUserByUsername(String username) {
		System.out.println(""+username);
		Optional<User> u = userRepository.findByUsername(username);
		if(u.isPresent()) {
			//System.out.println(u.get().getCustomerId());
			u.get().setPassword("");
			return u.get();
		}
		return null;
	}

	public UserUpdateDto updateUser(UserUpdateDto updatedUser) {
		UserUpdateDto dto = new UserUpdateDto();
		
		Optional<User> user = userRepository.findById(updatedUser.getCustomerId());
		if(user.isPresent()) {
			User u = user.get();
			u.setEmail(updatedUser.getEmail());
			u.setMobileNum(updatedUser.getMobileNum());
			u = userRepository.save(u);
			if(u!=null) {
				dto.setCustomerId(u.getCustomerId());
				dto.setEmail(u.getEmail());
				dto.setMobileNum(u.getMobileNum());
			}else {
				throw new BadCredentialsException("User Details not found to Update");
			}
		}else {
			throw new BadCredentialsException("User Details not found to Update");
		}
		return dto;
	}
	
	
	public Map<String,List<User>> getBeneficairies(String username){
		Map<String,List<User>> beneficiaries = new HashMap<>();
		List<User> users = userRepository.findAll();
		
		users  = users.stream().filter(u -> !u.getUsername().equals(username) && u.getAccounts().size()!=0).collect(Collectors.toList());
		
		beneficiaries.put("beneficiaries", users);
		System.out.println(beneficiaries);
		return beneficiaries;
	}
	


}
