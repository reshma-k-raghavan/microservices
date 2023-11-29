package com.ms.inventory.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

import com.ms.inventory.model.Inventory;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

	public Optional<Inventory> findBySkuCodeIgnoreCase(String skuCode);

	public List<Inventory> findAllBySkuCodeIn(List<String> skuCodeList);

}
