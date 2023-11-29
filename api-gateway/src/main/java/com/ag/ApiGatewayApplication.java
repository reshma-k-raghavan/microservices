package com.ag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiGatewayApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	/*
	// Code for creating Custom Filter corresponding to application property: spring.cloud.gateway.routes[3].filters[0] 
	@Bean
	public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder,
			CustomSetPathGatewayFilter customSetPathGatewayFilter) {
		
		return routeLocatorBuilder
				.routes()
				.route("discovery-server", predicateSpec -> predicateSpec
						.path("/eureka/web")
						.filters(filterSpec -> filterSpec
								.filter(customSetPathGatewayFilter)
								.setPath("/")
						)
						.uri(URI.create("http://localhost:8761"))
				)
				.build();
			
		
	}
	*/
	
}
