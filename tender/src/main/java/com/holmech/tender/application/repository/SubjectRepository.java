package com.holmech.tender.application.repository;

import com.holmech.tender.application.entity.Applicant;
import com.holmech.tender.application.entity.Subject;
import com.holmech.tender.application.entity.Tender;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SubjectRepository extends CrudRepository<Subject, Long> {

    List<Subject> findByTender(Tender tender, Sort sort);
    List<Subject> findByTender(Tender tender);
    List<Subject> findByTenderAndApplicant(Tender tender, Applicant applicant);
    List<Subject> findByMeetTrue();

    Subject findByApplicantAndNameSAndNumberSAndPrice(Applicant applicant, String nameS,Integer numberS, Double price);

    //void deleteAll(Iterable<Subject> subjects);
}
