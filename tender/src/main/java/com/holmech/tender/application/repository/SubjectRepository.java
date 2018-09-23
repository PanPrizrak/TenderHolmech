package com.holmech.tender.application.repository;

import com.holmech.tender.application.entity.Subject;
import com.holmech.tender.application.entity.Tender;
import org.springframework.data.repository.CrudRepository;

public interface SubjectRepository extends CrudRepository<Subject, Long> {
    Iterable<Subject> findByTender(Tender tender);
}
