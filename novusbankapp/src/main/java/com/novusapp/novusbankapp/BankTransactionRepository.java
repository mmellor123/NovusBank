package com.novusapp.novusbankapp;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BankTransactionRepository extends JpaRepository<BankTransaction, Long> {

	BankTransaction getById(Long id);
	
	//@Query("SELECT t from transactions t WHERE u.sender_id = ?1")
	//List<BankTransaction> getTransactions();
}
