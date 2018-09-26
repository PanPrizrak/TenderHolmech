package com.holmech.tender.application.repository;

import com.holmech.tender.application.entity.Tender;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TenderRepository extends CrudRepository<Tender,Long> {
    Tender findByNumberT(String numberT);
}
