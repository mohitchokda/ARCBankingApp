package com.example.ARCBank.Services;

import com.example.ARCBank.Dtos.LoginDto;
import com.example.ARCBank.Models.User;

public interface UserService {
	public String register(User user);
	public String login(LoginDto login);
	public User getUserByUsername(String username); 
}
