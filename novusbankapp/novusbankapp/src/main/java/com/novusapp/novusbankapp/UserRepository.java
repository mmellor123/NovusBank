package com.novusapp.novusbankapp;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long>{

	
	//@Query("SELECT u from USERS u WHERE u.mail = ?1")
	User findByEmail(String email);
	User findByAccountNumber(Long accountNumber);
	
	@Query(value = "SELECT t FROM BankTransaction t WHERE sender_account_number=?1 OR receiver_account_number=?1")
	List<BankTransaction> findTransactions(Long accountNumber);
}

