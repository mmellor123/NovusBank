package com.novusapp.novusbankapp;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {
	
	


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	
	
	public UserPrincipal(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority("USER"));
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getEmail();
	}
	
	public List<BankTransaction> getBankTransactions(){
		return user.getTransactions();
	}
	
	public String getEmail() {
		// TODO Auto-generated method stub
		return user.getEmail();
	}
	
	public Long getAccountNumber() {
		// TODO Auto-generated method stub
		return user.getAccountNumber();
	}
	
	public String getFirstName() {
		// TODO Auto-generated method stub
		return user.getFirstName();
	}
	
	public String getLastName() {
		// TODO Auto-generated method stub
		return user.getLastName();
	}
	
	public int getBalance() {
		// TODO Auto-generated method stub
		return user.getBalance();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
