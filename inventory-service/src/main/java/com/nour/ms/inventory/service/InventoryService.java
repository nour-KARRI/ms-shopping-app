package com.nour.ms.inventory.service;

import com.nour.ms.inventory.reporditory.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class InventoryService {
	private final InventoryRepository repo;

	public boolean IsInStock(String skuCode, Integer quantity){
		return repo.existsBySkuCodeAndQuantityIsGreaterThanEqual(skuCode, quantity);
	}
}
