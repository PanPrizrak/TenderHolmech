package com.holmech.tender.application.repository;

import com.holmech.tender.application.entity.Subject;
import org.springframework.data.repository.CrudRepository;

public interface SubjectRepository extends CrudRepository<Subject, Long> {
}
