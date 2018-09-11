package com.holmech.tender.application.repository;

import com.holmech.tender.application.entity.Tender;
import org.springframework.data.repository.CrudRepository;

public interface TenderRepository extends CrudRepository<Tender,Long> {
    Iterable<Tender> findByNameT(String filter);

    Tender findOne(Long id);
}
