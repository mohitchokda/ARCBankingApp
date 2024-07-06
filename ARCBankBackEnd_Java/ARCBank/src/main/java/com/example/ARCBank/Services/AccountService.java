package com.example.ARCBank.Services;

import java.util.List;

import com.example.ARCBank.Models.Accounts;

public interface AccountService {
	public List<Accounts> getAccounts(int customerId);
}
