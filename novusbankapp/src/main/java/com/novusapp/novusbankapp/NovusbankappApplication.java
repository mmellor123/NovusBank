package com.novusapp.novusbankapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class NovusbankappApplication extends SpringBootServletInitializer{

	@Autowired
	private UserRepository repo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {  
	      return application.sources(SpringApplicationBuilder.class);
	}
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(NovusbankappApplication.class, args);
		
		
	}

}
