package com.ag.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
/*
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
*/

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
	/*
	 * When OAuth2 Resource Server Keycloak starts through Docker compose, it imports the realm (spring-boot-microservices-realm) from Docker compose. Without using Docker compose, the realm must be created manually in Keycloak resource server.
	 * For granting access to application services for each user, a client secret is required, which is generated by Keycloak under Client credentials. The secret is sent to the user who wishes to access the application service API.
	 * And when a new service request is sent by the user through HTTP client, the client secret should be used to generate the Bearer/Access Token from url: http://localhost:8080/realms/spring-boot-microservices-realm/protocol/openid-connect/token, and the token will be sent along with the request.
	 * The access token will be validated by the SecurityWebFilter chain class using default configuration. 
	 * 
	 * Alternatively, SecurityWebFilter can also use JWT Validation by sending a REST template GET request to oauth2.resourceserver.jwt.issuer-uri (keycloak server)
	 * And use the JwtDecoder to decode the token and validate the access token. The code is commented out in this file.
	 */
	
	/*
	// JWT Validation through issuer-uri
	@Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
	private String issueUri;
	
	// If this bean is enabled, serverHttpSecurity.jwt(Customizer.withDefaults()) will call jwtDecoder() method implicitly.
	@Bean
	public JwtDecoder jwtDecoder() {
		return JwtDecoders.fromOidcIssuerLocation(issueUri);
	}
	*/
	
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
	
	

}
