package com.novusapp.novusbankapp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.extras.springsecurity5.util.SpringSecurityContextUtils;


@Controller
public class HomeController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private BankTransactionRepository bankRepo;
	
	
	
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	
	@RequestMapping("/")
	public String home(@AuthenticationPrincipal UserPrincipal user, Model model) {
		//User user = repo.findByUsername(userPrincipal.getUsername());
		User u = repo.findByAccountNumber(user.getAccountNumber());
		model.addAttribute("user", u);
		return "index.html";
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("user", new User());
		return "register.html";
	}
	
	@GetMapping("/logout-success")
	public String logoutSuccess() {
		return "logout-success.html";
	}
	
	@PostMapping("/register")
	public String registerPost(HttpServletRequest request, HttpServletResponse response) {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String balance = request.getParameter("balance");
		User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setBalance(Integer.parseInt(balance));
        
		repo.save(user);
		return "login.html";
	}
	
	/**Seperate transactions that user sent and the ones received;
	private static Map<BankTransaction, Boolean> mapTransactions(List<BankTransaction> transactions, Long accountNumber) {
		Map<BankTransaction, Boolean> transactionMap = new HashMap<BankTransaction, Boolean>(){{}};
		for(BankTransaction transaction : transactions) {
			if(transaction.getSender().getAccountNumber() == accountNumber) {
				transactionMap.put(transaction, true);
			}
			else {
				transactionMap.put(transaction, false);
			}
		}
		return transactionMap;
	}**/
	
	@GetMapping("/list_transactions")
	public String listTransactions(@AuthenticationPrincipal UserPrincipal user, Model model) {
		
		List<BankTransaction> transactions = repo.findTransactions(user.getAccountNumber());
		model.addAttribute("transactions", transactions);
		User u = repo.findByAccountNumber(user.getAccountNumber());
		model.addAttribute("user", u);
		System.out.println(user.getAuthorities());
		return "list_transactions.html";
	}
	
	@PostMapping("/list_transactions")
	public String listTransactions(@AuthenticationPrincipal UserPrincipal user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Long receiverAccountNumber = Long.parseLong(request.getParameter("receiverAccountNumber"));
		int amount = Integer.parseInt(request.getParameter("amount"));
		//User receiver = repo.findByAccountNumber(receiverAccountNumber);
		//User sender = repo.findByAccountNumber(user.getAccountNumber());
		processTransaction(user, receiverAccountNumber, amount);
		return "redirect:/list_transactions";
	}
	
	@GetMapping("/add_money")
	public String addMoney(@AuthenticationPrincipal UserPrincipal user, Model model) {
		
		List<BankTransaction> transactions = repo.findTransactions(user.getAccountNumber());
		model.addAttribute("transactions", transactions);
		User u = repo.findByAccountNumber(user.getAccountNumber());
		model.addAttribute("user", u);
		System.out.println(user.getAuthorities());
		return "add_money.html";
	}
	
	@PostMapping("/add_money")
	public String addMoney(@AuthenticationPrincipal UserPrincipal user, Model model, HttpServletRequest request, HttpServletResponse response) {
		
		User u = repo.findByAccountNumber(user.getAccountNumber());
		int current_balance = u.getBalance();
		int amount = Integer.parseInt(request.getParameter("amount"));
		u.setBalance(current_balance + amount);
		repo.save(u);
		return "redirect:/list_transactions";
	}
	
	//TODO: will be used to process transaction/validate
	private void processTransaction(@AuthenticationPrincipal UserPrincipal user, Long receiverAccountNumber, int amount) {
		User receiver = repo.findByAccountNumber(receiverAccountNumber);
		User sender = repo.findByAccountNumber(user.getAccountNumber());
		if(receiver == null) {
			System.out.println("No user found");
		}
		else if(receiverAccountNumber == sender.getAccountNumber()) {
			System.out.println("You cant send money to yourself");
		}
		else if(amount < 0 ) {
			System.out.println("Balance too low");
		}
		else if(sender.getBalance() < amount) {
			System.out.println("Failed to send");
		}
		else {
			int receiverBalance = receiver.getBalance() + amount;
			int senderBalance = sender.getBalance() - amount;
			receiver.setBalance(receiverBalance);
			sender.setBalance(senderBalance);
			BankTransaction transaction = new BankTransaction();
			transaction.setAmount(amount);
			transaction.setDate(new Date());
			transaction.setReceiver(receiver);
			transaction.setSender(sender);
			transaction.setSenderBalance(senderBalance);
			transaction.setReceiverBalance(receiverBalance);
			sender.addTransaction(transaction);
			bankRepo.save(transaction);
			
			authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);


			System.out.println("Transaction saved succesfully");
		}

	}
	

}
