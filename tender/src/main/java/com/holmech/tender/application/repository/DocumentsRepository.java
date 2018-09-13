package com.holmech.tender.application.repository;

import com.holmech.tender.application.entity.Documents;
import org.springframework.data.repository.CrudRepository;

public interface DocumentsRepository extends CrudRepository<Documents, Long> {
}
