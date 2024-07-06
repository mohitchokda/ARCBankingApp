package com.example.ARCBank.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ARCBank.Models.Accounts;
import com.example.ARCBank.Services.Impl.AccountServiceImpl;

@RestController
@CrossOrigin("http://localhost:3000/")
public class AccountController {
	
	@Autowired
	private AccountServiceImpl accountService;
	//CRUD
	//Create
	@PostMapping("/{customerId}/accounts")
	public String createAccount(@PathVariable int customerId,@RequestBody Accounts acc) {
		return accountService.addAccount(acc, customerId);
	}
	//Read
	@GetMapping("/{customerId}/accounts")
	public Map<String,List<Accounts>> getAllAccounts(@PathVariable int customerId){
		Map<String,List<Accounts>> map=new HashMap<>();
		map.put("accounts",accountService.getAccounts(customerId));
		return map;
	}
	
	@GetMapping("/{customerId}/accounts/{accountNum}")
	public Accounts getAllAccounts(@PathVariable int customerId,@PathVariable int accountNum){
		return accountService.getAccountByAccountNum(customerId,accountNum);
	}
	
	

}
