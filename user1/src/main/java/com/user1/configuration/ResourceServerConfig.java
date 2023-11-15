//package com.user1.configuration;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.web.csrf.CsrfTokenRepository;
//import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
//
//@SuppressWarnings("deprecation")
//@Configuration
//@EnableWebSecurity
//public class ResourceServerConfig  extends WebSecurityConfigurerAdapter{
//	private CsrfTokenRepository csrfTokenRepository() 
//	{ 
//	    HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository(); 
//	    repository.setSessionAttributeName("_csrf");
//	    return repository; 
//	}
//	@Override
//	 protected void configure(HttpSecurity http) throws Exception {
//	     http.csrf()
//	     .csrfTokenRepository(csrfTokenRepository());
//	}
//}