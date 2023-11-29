package com.ms.inventory.dto;

//Request object to query an Inventory object based on specific skuCode and quantity
public class InventoryRequest {

	private String skuCode;

	private Integer quantity;

	protected InventoryRequest(String skuCode, Integer quantity) {
		super();
		this.skuCode = skuCode;
		this.quantity = quantity;
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

}
