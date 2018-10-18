package com.holmech.tender.application.repository;

import com.holmech.tender.application.entity.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
    @Query(value = "SELECT * FROM orders WHERE idO=LAST_INSERT_ID()", nativeQuery = true)
    Order findLastOrder();

    Order findByNumberO(String NumberO);
}
