package com.holmech.tender.application.service;

import com.holmech.tender.application.entity.Subject;
import com.holmech.tender.application.entity.SubjectAfterTheReduction;
import com.holmech.tender.application.repository.SubjectAfterTheReductionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectAfterTheReductionService {

    private final SubjectAfterTheReductionRepository subjectAfterTheReductionRepository;

    public SubjectAfterTheReductionService(SubjectAfterTheReductionRepository subjectAfterTheReductionRepository) {
        this.subjectAfterTheReductionRepository = subjectAfterTheReductionRepository;
    }

    public SubjectAfterTheReduction findBySubject(Subject subject){
        return subjectAfterTheReductionRepository.findBySubject(subject);
    }

    public List<SubjectAfterTheReduction> findBySubjectIn(List<Subject> subjectList){
        return subjectAfterTheReductionRepository.findBySubjectIn(subjectList);
    }

    public void saveAll(List<SubjectAfterTheReduction> subjectAfterTheReductionList) {
        subjectAfterTheReductionRepository.saveAll( (Iterable<SubjectAfterTheReduction>) subjectAfterTheReductionList);
    }
}
