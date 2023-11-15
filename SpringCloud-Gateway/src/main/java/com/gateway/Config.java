package com.gateway;

import org.springframework.context.annotation.Bean; 
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class Config {
	
	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		http.csrf().disable()
				.authorizeExchange()
				.anyExchange()
				.authenticated()
			.and()
				.oauth2Login()
			.and()
				.oauth2ResourceServer()
				.jwt(); // to redirect to oauth2 login page.
		http.cors();
		return http.build();
	}

}
