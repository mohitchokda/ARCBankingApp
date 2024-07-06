package com.example.ARCBank.Dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class UserUpdateDto {
	
	@NotNull
	private int customerId;
	
	@Email
	private String email;
	
	@NotBlank(message = "Mobile number cannot be Empty")
	@Pattern(regexp = "(^$|[0-9]{10})" , message = "Enter a Valid Mobile Number")
	private String mobileNum;

	public int getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(int c) {
		this.customerId = c;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}
}
