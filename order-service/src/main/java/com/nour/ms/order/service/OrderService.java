package com.nour.ms.order.service;

import com.nour.ms.order.client.InventoryClient;
import com.nour.ms.order.dto.OrderRequest;
import com.nour.ms.order.model.Order;
import com.nour.ms.order.repository.OrderRepository;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service @RequiredArgsConstructor @Slf4j
public class OrderService {

	Logger log = LoggerFactory.getLogger(OrderService.class);

	private final OrderRepository orderRepository;
	private final InventoryClient inventoryClient;
	public String placeOrder(OrderRequest orderRequest){

		var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());
		if (isProductInStock){
			Order order = Order.builder()
					.orderNumber(UUID.randomUUID().toString())
					.skuCode(orderRequest.skuCode())
					.price(orderRequest.price())
					.quantity(orderRequest.quantity())
					.build();
			orderRepository.save(order);
			return "Order placed Successfully";
		}else{
			log.error("Product with SkuCode " + orderRequest.skuCode() + " is not in stock");
			return "Ordering failed";
		}
	}
}
