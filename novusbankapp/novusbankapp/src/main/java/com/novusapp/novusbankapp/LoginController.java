package com.novusapp.novusbankapp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	
	
	
	@GetMapping("/login")
	public String login() {
		return "login.html";
	}
	

	
	@RequestMapping("/logout-success")
	public String logoutPage() {
		return "logout.jsp";
	}

}
