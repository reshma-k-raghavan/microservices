package com.ms.order.dto;

import java.math.BigDecimal;

public class OrderLineItemDtoRequest {

	private String skuCode;

	private BigDecimal price;

	private Integer quantity;

	public OrderLineItemDtoRequest(String skuCode, BigDecimal price, Integer quantity) {
		super();
		this.skuCode = skuCode;
		this.price = price;
		this.quantity = quantity;
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
