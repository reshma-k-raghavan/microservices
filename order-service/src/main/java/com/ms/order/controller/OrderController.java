package com.ms.order.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import io.github.resilience4j.retry.annotation.Retry;

import com.ms.order.dto.CircuitBreakerOrderResponse;
import com.ms.order.dto.OrderRequest;
import com.ms.order.dto.OrderResponse;
import com.ms.order.service.OrderService;

import java.util.concurrent.CompletableFuture;

@RequestMapping("/api/order")
@RestController
public class OrderController {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;

	// Client makes a call to order-service, which in turn calls inventory-service
	// to check stock.
	// Use Resilience4j circuit-breaker for fault tolerance
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	@CircuitBreaker(name = "inventory", fallbackMethod = "placeOrderFallback")
	@TimeLimiter(name = "inventoryTimeLimiter")
	@Retry(name = "inventoryRetry")
	public CompletableFuture<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest) {
		// A new request will be created on using CompletableFuture.sendAsAsync(). And
		// hence traceId will be different for inventory-service.
		return CompletableFuture.supplyAsync(() -> orderService.placeOrder(orderRequest));
	}

	// Fallback method when inventory service call fails, order-service show show an
	// error message
	public CompletableFuture<OrderResponse> placeOrderFallback(OrderRequest orderRequest,
			RuntimeException runtimeException) {
		return CompletableFuture.supplyAsync(
				() -> new CircuitBreakerOrderResponse("Oops! Something went wrong. Please try again after sometime."));
	}

}
