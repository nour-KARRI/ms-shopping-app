package com.nour.ms.product.dto;

import java.math.BigDecimal;

public record ProductRequest(String id, String skuCode, String name, String description, BigDecimal price) {}
