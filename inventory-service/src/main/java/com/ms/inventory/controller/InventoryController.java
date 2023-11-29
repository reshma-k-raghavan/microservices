package com.ms.inventory.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.ms.inventory.dto.InventoryResponse;
import com.ms.inventory.service.InventoryService;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InventoryController.class);

	@Autowired
	private InventoryService inventoryService;

	// http://localhost:8082/api/inventory/SH
	@GetMapping(path = "/{sku-code}")
	@ResponseStatus(code = HttpStatus.OK)
	public InventoryResponse fetchInventoryBySkuCode(@PathVariable("sku-code") String skuCode) {
		return inventoryService.fetchInventoryBySkuCode(skuCode);
	}

	// http://localhost:8082/api/inventory?sku-code=SH&sku-code=FL
	@GetMapping
	public List<InventoryResponse> fetchAllInventoryBySkuCodes(@RequestParam("sku-code") List<String> skuCodeList) {
		return inventoryService.fetchAllInventoryBySkuCodes(skuCodeList);
	}

	// Other mappings that can be created:
	// Send list of sku-code as Path variables: http://localhost:8082/api/inventory/SH,PA,MZ
	// Send list of sku-code as Request params: http://localhost:8082/api/inventory?sku-code=SH&sku-code=PA
}
