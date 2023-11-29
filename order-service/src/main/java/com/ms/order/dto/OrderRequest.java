package com.ms.order.dto;

import java.util.List;

public class OrderRequest {

	private List<OrderLineItemDtoRequest> orderLineItemDtoRequestList;

	public List<OrderLineItemDtoRequest> getOrderLineItemDtoRequestList() {
		return orderLineItemDtoRequestList;
	}

	public void setOrderLineItemDtoRequestList(List<OrderLineItemDtoRequest> orderLineItemDtoRequestList) {
		this.orderLineItemDtoRequestList = orderLineItemDtoRequestList;
	}

}
