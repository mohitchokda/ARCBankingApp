package com.example.ARCBank.Services.Impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.example.ARCBank.Models.Accounts;
import com.example.ARCBank.Models.User;
import com.example.ARCBank.Repositories.AccountsRepository;
import com.example.ARCBank.Repositories.UserRepository;
import com.example.ARCBank.Services.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private AccountsRepository accRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	//GET
	//@Override
	public List<Accounts> getAccounts(int customerId) {
		List<Accounts> accounts = accRepo.findByUser_customerId(customerId);
		
//		Optional<User> user = userRepo.findById(customerId);
//		if(user.isPresent()) {
//			User u = user.get();
//			if(u.getAccounts()!=null && u.getAccounts().size()>0) {
//				accounts = new ArrayList<>(u.getAccounts());
//				return accounts;
//			}else throw new BadCredentialsException("No Accounts Found for the User");
//		}
////		accRepo.findByCustomerId(customerId);
////		for(Accounts a :accounts) {
////			System.out.println("ACC : "+a.getAccountNumber());
////		}
		if(accounts!=null && accounts.size() > 0) {
			return accounts;
		}
		else throw new BadCredentialsException("No Accounts Found for the User");
	}
	
	//Post
	public String addAccount(Accounts acc,int custId) {
		User user = userRepo.findByCustomerId(custId);
		acc.setUser(user);
		if(accRepo.save(acc)!=null) {
			return "Account added successfully";
		}
		else throw new BadCredentialsException("Cannot add Account");
	}

	public Accounts getAccountByAccountNum(int customerId, int accountNum) {
		
		return null;
	}

}
