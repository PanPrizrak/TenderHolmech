package com.holmech.tender.application.repository;

import com.holmech.tender.application.entity.Order;
import com.holmech.tender.application.entity.WorkerR;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WorkerRReposytory extends CrudRepository<WorkerR, Long> {

    List<WorkerR> findByOrder(Order order);
}
