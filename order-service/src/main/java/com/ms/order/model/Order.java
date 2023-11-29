package com.ms.order.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

// Using 'order' table name in MySQL db caused error. So changed to 'tb_order'
@Entity
@Table(name = "tb_order")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;

	private String orderNumber;

	// Default fetch is lazy for OneToMany.
	// CascadeType.ALL - Save OrderLineItem on saving Order
	@OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<OrderLineItem> orderLineItemList;

	public Order() {
	}

	public Order(String orderNumber, List<OrderLineItem> orderLineItemList) {
		this.orderNumber = orderNumber;
		this.orderLineItemList = orderLineItemList;
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

	public List<OrderLineItem> getOrderLineItemList() {
		return orderLineItemList;
	}

	public void setOrderLineItemList(List<OrderLineItem> orderLineItemList) {
		this.orderLineItemList = orderLineItemList;
	}

}
