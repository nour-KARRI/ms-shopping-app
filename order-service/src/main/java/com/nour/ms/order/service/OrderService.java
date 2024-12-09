package com.nour.ms.order.service;

import com.nour.ms.order.client.InventoryClient;
import com.nour.ms.order.dto.OrderRequest;
import com.nour.ms.order.event.OrderPlacedEvent;
import com.nour.ms.order.model.Order;
import com.nour.ms.order.repository.OrderRepository;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service @RequiredArgsConstructor @Slf4j
public class OrderService {

	Logger log = LoggerFactory.getLogger(OrderService.class);

	private final OrderRepository orderRepository;
	private final InventoryClient inventoryClient;
	private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;
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

			OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent(order.getOrderNumber(), orderRequest.userDetails().email());

			/** send a message to kafkaTopic:
			 	- key: order-placed
			 	- value: OrderNumber, email
			 */
			log.info("Start - Sending OrderPlaceEvent {} to Kafka topic order-placed", orderPlacedEvent);
			kafkaTemplate.send("orderPlaced", orderPlacedEvent);
			log.info("End - Sending OrderPlacedEvent {} to kafka topic order-placed", orderPlacedEvent);

			return "Order placed Successfully";
		}else{
			log.error("Product with SkuCode {} is not in stock", orderRequest.skuCode());
			return "Ordering failed";
		}
	}
}
