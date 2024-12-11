package com.nour.ms.inventory.reporditory;

import com.nour.ms.inventory.model.Inventory;
import io.micrometer.observation.annotation.Observed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Observed
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
	boolean existsBySkuCodeAndQuantityIsGreaterThanEqual(String skuCode, Integer quantity);
}
