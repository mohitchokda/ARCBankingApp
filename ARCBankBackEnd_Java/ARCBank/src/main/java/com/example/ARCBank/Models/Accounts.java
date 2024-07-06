package com.example.ARCBank.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "accounts")
public class Accounts {

	@Id
	@Column(name = "accountNum")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@SequenceGenerator(name = "seqGen",sequenceName = "my_sequence_name",allocationSize = 1)
	private int accountNumber;
	
	@NotNull
	private String type;
	
	@NotNull
	private double balance;
	
	//Relations create infinite recursion or deeply nested structures in the JSON output.
	//To Solve this we use 
	/*
	 * To avoid nesting issues in OneToMany relationships, you can:
		Use @JsonIgnore on one side of the relationship to prevent serialization of that side.
		Use @JsonManagedReference and @JsonBackReference to manage bidirectional relationships.
		Use DTOs to have full control over what gets serialized.
	 */

	//table entity field name //User Entity Name 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id",referencedColumnName = "customer_id")
	@JsonIgnore
	private User user;
	
	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
