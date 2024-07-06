package com.example.ARCBank.Models;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "userdetails")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id")
	private int customerId;
	
	@Size(min=3,message = "firstname must contain atleast 3 characters")
	private String firstname;
	
	@NotBlank(message = "Lastname cannot be empty")
	private String lastname;
	
	@NotBlank(message = "Role cannot be Empty")
	private String role;
	
	@Email
	private String email;
	
	@NotBlank(message = "Mobile number cannot be Empty")
	@Pattern(regexp = "(^$|[0-9]{10})" , message = "Enter a Valid Mobile Number")
	private String mobileNum;
	
	@NotBlank(message = "Username cannot be Empty")
	@Size(min=5,message = "Username must contain atleast 5 characters")
	private String username;
	
	@NotBlank(message = "Password cannot be Empty")
	@Size(min=8,message = "Password must contain atleast 8 characters")
	private String password;
	
	//User Account Details //Must mention the variable name of mappedUser in Accounts  
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)	
	private Set<Accounts> accounts;
	
	public User() {
	}
	
	public User(int customerId, String firstname, String lastname, String role, String email, String mobileNum,
			String username, String password) {
		this.customerId = customerId;
		this.firstname = firstname;
		this.lastname = lastname;
		this.role = role;
		this.email = email;
		this.mobileNum = mobileNum;
		this.username = username;
		this.password = password;
	}
	
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int cusId) {
		this.customerId = cusId;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Set<Accounts> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<Accounts> accounts) {
		this.accounts = accounts;
	}
}
