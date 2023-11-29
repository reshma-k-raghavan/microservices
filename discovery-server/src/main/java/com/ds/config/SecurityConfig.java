package com.ds.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

// Security is enabled through Spring Web Security instead of WebFlux. Hence 
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Value("${eu.username}")
	private String username;

	@Value("${eu.password}")
	private String password;

	// Allow restricting access based on HttpServletRequest and RequestMatcher
	// implementations using URL Patterns
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) {
		try {
			httpSecurity
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(auth -> auth
							.anyRequest()
							.authenticated())
							.httpBasic(Customizer.withDefaults());
			return httpSecurity.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// Add In-Memory User Store with single user for demo purpose
	@Bean
	public InMemoryUserDetailsManager userDetailsService() {
		// Here instead of withDefaultPasswordEncoder(), hashing of passwords should be
		// used in Prod.
		// User.withDefaultPasswordEncoder().username("").password("").build();
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		UserDetails userDetails = User.builder()
				.passwordEncoder(pwd -> passwordEncoder.encode(password != null ? password : "password"))
				.username(username != null ? username : "eureka")
				.roles("USER")
				.build();
		return new InMemoryUserDetailsManager(userDetails);
	}

}
