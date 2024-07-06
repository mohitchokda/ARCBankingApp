package com.example.ARCBank.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ARCBank.Models.Accounts;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Integer> {
						//Nested parameter query User.customerId 
	public List<Accounts> findByUser_customerId(int customerId);
	public Accounts findByAccountNumber(int accNo);
	
}
