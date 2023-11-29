package com.ms.order.dto;

public class InventoryResponse {
	private Long inventoryId;

	private String skuCode;

	private Integer quantity;

	private boolean inStock;

	public InventoryResponse() {
	}

	public InventoryResponse(Long inventoryId, String skuCode, Integer quantity, boolean inStock) {
		super();
		this.inventoryId = inventoryId;
		this.skuCode = skuCode;
		this.quantity = quantity;
		this.inStock = inStock;
	}

	public Long getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(Long inventoryId) {
		this.inventoryId = inventoryId;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public boolean isInStock() {
		return inStock;
	}

	public void setInStock(boolean inStock) {
		this.inStock = inStock;
	}

}
