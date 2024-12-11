package com.nour.ms.order.repository;

import com.nour.ms.order.model.Order;
import io.micrometer.observation.annotation.Observed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Observed
public interface OrderRepository extends JpaRepository<Order, Long> {
}
