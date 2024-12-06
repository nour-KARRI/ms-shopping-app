package com.nour.ms.product.dto;

import java.math.BigDecimal;

public record ProductResponse(String id, String skuCode, String name, String description, BigDecimal price) {
}
