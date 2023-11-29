package com.ms.order.dto;

import java.math.BigDecimal;

public class OrderLineItemDtoResponse {

	private Long orderLineItemId;

	private String skuCode;

	private BigDecimal price;

	private Integer quantity;

	public OrderLineItemDtoResponse(Long orderLineItemId, String skuCode, BigDecimal price, Integer quantity) {
		super();
		this.orderLineItemId = orderLineItemId;
		this.skuCode = skuCode;
		this.price = price;
		this.quantity = quantity;
	}

	public Long getOrderLineItemId() {
		return orderLineItemId;
	}

	public void setOrderLineItemId(Long orderLineItemId) {
		this.orderLineItemId = orderLineItemId;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
