package com.nour.ms.order.controller;

import com.nour.ms.order.dto.OrderRequest;
import com.nour.ms.order.model.Order;
import com.nour.ms.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order") @RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@PostMapping
	//@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest){
		String response = orderService.placeOrder(orderRequest);
		if (response == "Order placed Successfully"){
			return new ResponseEntity<>("Order placed Successfully", HttpStatus.OK) ;
		}else{
			return new ResponseEntity<>("Ordering failed", HttpStatus.BAD_REQUEST) ;
		}

	}
}
