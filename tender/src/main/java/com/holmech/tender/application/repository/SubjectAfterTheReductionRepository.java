package com.holmech.tender.application.repository;

import com.holmech.tender.application.entity.Subject;
import com.holmech.tender.application.entity.SubjectAfterTheReduction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SubjectAfterTheReductionRepository extends CrudRepository<SubjectAfterTheReduction, Long> {
    SubjectAfterTheReduction findBySubject(Subject subject);
    List<SubjectAfterTheReduction> findBySubjectIn(List<Subject> subjectList);
}
