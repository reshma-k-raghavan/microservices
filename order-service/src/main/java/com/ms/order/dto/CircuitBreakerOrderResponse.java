package com.ms.order.dto;

public class CircuitBreakerOrderResponse extends OrderResponse {

	private String errorMessage;

	public CircuitBreakerOrderResponse(String errorMessage) {
		super(0L, null, null);
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
