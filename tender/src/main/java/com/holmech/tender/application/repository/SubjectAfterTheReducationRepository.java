package com.holmech.tender.application.repository;

import com.holmech.tender.application.entity.Subject;
import com.holmech.tender.application.entity.SubjectAfterTheReduction;
import org.springframework.data.repository.CrudRepository;

public interface SubjectAfterTheReducationRepository extends CrudRepository<SubjectAfterTheReduction, Long> {
    SubjectAfterTheReduction findBySubject(Subject subject);
}
