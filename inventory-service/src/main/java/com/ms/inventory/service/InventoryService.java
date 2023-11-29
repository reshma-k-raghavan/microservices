package com.ms.inventory.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ms.inventory.dto.InventoryResponse;
import com.ms.inventory.model.Inventory;
import com.ms.inventory.repository.InventoryRepository;

import lombok.SneakyThrows;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Service
@Transactional
public class InventoryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(InventoryService.class);

	@Autowired
	private InventoryRepository inventoryRepository;

	public InventoryResponse fetchInventoryBySkuCode(String skuCode) {
		Optional<Inventory> op = inventoryRepository.findBySkuCodeIgnoreCase(skuCode);
		InventoryResponse inventoryResponse = new InventoryResponse();
		if (op.isPresent()) {
			Inventory inventory = op.get();
			inventoryResponse.setInventoryId(inventory.getInventoryId());
			inventoryResponse.setSkuCode(inventory.getSkuCode());
			inventoryResponse.setQuantity(inventory.getQuantity());
			inventoryResponse.setInStock(inventory.getQuantity() > 0 ? true : false);
		}
		return inventoryResponse;
	}

	// @SneakyThrows Lombok's annotation for adding try-catch for InterruptedException
	public List<InventoryResponse> fetchAllInventoryBySkuCodes(List<String> skuCodeList) {
		/*
		// Circuit Breaker Service Delay Simulation Code.
		LOGGER.info("Waiting for 10s to simulate delay in service for Circuit-Breaker implementation");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		LOGGER.info("Finished Waiting!");
		*/
		List<Inventory> inventoryList = inventoryRepository.findAllBySkuCodeIn(skuCodeList);
		LOGGER.info("Retrived SkuCodes based on given list");
		return inventoryList.stream().map(
				il -> new InventoryResponse(il.getInventoryId(), il.getSkuCode(), il.getQuantity(), il.getInStock()))
				.toList();
	}

}
