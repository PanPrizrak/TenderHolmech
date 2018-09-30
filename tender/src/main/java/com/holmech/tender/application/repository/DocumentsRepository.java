package com.holmech.tender.application.repository;

import com.holmech.tender.application.entity.Applicant;
import com.holmech.tender.application.entity.Documents;
import com.holmech.tender.application.entity.Tender;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DocumentsRepository extends CrudRepository<Documents, Long> {

    Documents findByTenderAndApplicant(Tender tender, Applicant applicant);

    List<Documents> findByTender(Tender tender);
}
