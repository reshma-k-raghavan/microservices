package com.ms.order.dto;

import java.util.List;

public class OrderResponse {

	private Long orderId;

	private String orderNumber;

	private List<OrderLineItemDtoResponse> orderLineItemDtoResponseList;

	public OrderResponse(Long orderId, String orderNumber,
			List<OrderLineItemDtoResponse> orderLineItemDtoResponseList) {
		super();
		this.orderId = orderId;
		this.orderNumber = orderNumber;
		this.orderLineItemDtoResponseList = orderLineItemDtoResponseList;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public List<OrderLineItemDtoResponse> getOrderLineItemDtoResponseList() {
		return orderLineItemDtoResponseList;
	}

	public void setOrderLineItemDtoResponseList(List<OrderLineItemDtoResponse> orderLineItemDtoResponseList) {
		this.orderLineItemDtoResponseList = orderLineItemDtoResponseList;
	}

}
