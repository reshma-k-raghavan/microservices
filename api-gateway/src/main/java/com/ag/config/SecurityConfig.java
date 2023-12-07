package com.ag.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

	@Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
	private String issueUri;
	
	@Bean
	public SecurityWebFilterChain securityWebFilterchain(ServerHttpSecurity serverHttpSecurity) {
		// It checks if all service endpoints are secure or not
		// Disable CSRF as requests will be sent through only Httpie client and no public servers are used for authorization.
		// No user credential exchange for access token is required for accessing Eureka server static pages with url: /eureka/**. Hence permit all requests to this url. And for any other uri/anyExchange, require authentication
		// Configure OAuth2 JWT Resource server support
		serverHttpSecurity
				.csrf(csrf->csrf.disable())
				.authorizeExchange(authorizeExchangeSpec -> authorizeExchangeSpec
						.pathMatchers("eureka/**")
						.permitAll()
						.anyExchange()
						.authenticated())
				.oauth2ResourceServer(oauth2ResourceServerSpec -> oauth2ResourceServerSpec
						.jwt(Customizer.withDefaults()));
		
		return serverHttpSecurity.build();
	}
	
	@Bean
	public JwtDecoder jwtDecoder() {
		return JwtDecoders.fromOidcIssuerLocation(issueUri);
	}

}
