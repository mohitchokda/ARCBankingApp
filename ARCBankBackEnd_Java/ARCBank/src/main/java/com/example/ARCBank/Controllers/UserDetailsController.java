package com.example.ARCBank.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ARCBank.Dtos.LoginDto;
import com.example.ARCBank.Dtos.UserUpdateDto;
import com.example.ARCBank.Jwt.JwtService;
import com.example.ARCBank.Models.User;
import com.example.ARCBank.Services.Impl.UserServiceImpl;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("http://localhost:3000/")
@RequestMapping("/user")
public class UserDetailsController {
	
	@Autowired
	UserServiceImpl userService;
	
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@Valid @RequestBody User user) {
		
		ResponseEntity<String> rs = null;
		String response = userService.register(user);
		rs = new ResponseEntity<String>(response,HttpStatus.OK);
		return rs;
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@Valid @RequestBody LoginDto login){
		String token = userService.login(login);
		ResponseEntity<String> rs =  new ResponseEntity<String>(token,HttpStatus.OK);
		return rs;
	}
	
	@PutMapping("/update")
	public ResponseEntity<UserUpdateDto> updateUser(@Valid @RequestBody UserUpdateDto updatedUser){
		//System.out.println(updatedUuser.getEmail() + " "+updatedUuser.getMobileNum());
		UserUpdateDto user = userService.updateUser(updatedUser);
		return new ResponseEntity<UserUpdateDto>(user,HttpStatus.OK);
	}
	
	
	@GetMapping("/{name}")
	public ResponseEntity<User> getUserDetails(@PathVariable String name) {
		User user = userService.getUserByUsername(name);
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//System.out.println("Curr Auth : "+auth.getName());
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	
	@GetMapping("{name}/beneficiary")
	public ResponseEntity<List<User>> getBeneficiaryDetails(@PathVariable String name) {
		List<User> users = userService.getBeneficairies(name).get("beneficiaries");
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//System.out.println("Curr Auth : "+auth.getName());
		return new ResponseEntity<List<User>>(users,HttpStatus.OK);
	}

}
