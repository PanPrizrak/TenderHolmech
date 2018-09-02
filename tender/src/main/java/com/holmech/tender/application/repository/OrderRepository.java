package com.holmech.tender.application.repository;

import com.holmech.tender.application.entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
