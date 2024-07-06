package com.example.ARCBank.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ARCBank.Models.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
	public User findByEmail(String email);
	public User findByCustomerId(int custId);
	public Optional<User> findByUsername(String username);
	//public List<User> findAllExceptUsername(String username);
	
//	@Query("update User u"
//			+ "set u.mobileNum = ?"+", "
//					+ "u.email = ? where customerId = ?")
//	public boolean updateUser(String mobileNum,String email); 
}
