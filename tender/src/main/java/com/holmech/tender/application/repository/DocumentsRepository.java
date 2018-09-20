package com.holmech.tender.application.repository;

import com.holmech.tender.application.entity.Applicant;
import com.holmech.tender.application.entity.Documents;
import com.holmech.tender.application.entity.Tender;
import org.springframework.data.repository.CrudRepository;

public interface DocumentsRepository extends CrudRepository<Documents, Long> {

    Documents findByTenderAndApplicant(Tender tender, Applicant applicant);

    Iterable<Documents> findByTender(Tender tender);
}
