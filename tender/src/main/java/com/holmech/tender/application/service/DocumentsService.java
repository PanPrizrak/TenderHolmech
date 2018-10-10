package com.holmech.tender.application.service;

import com.holmech.tender.application.entity.Applicant;
import com.holmech.tender.application.entity.Documents;
import com.holmech.tender.application.entity.Tender;
import com.holmech.tender.application.repository.ApplicantRepository;
import com.holmech.tender.application.repository.DocumentsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class DocumentsService {

    private final DocumentsRepository documentsRepository;
    private final ApplicantRepository applicantRepository;

    public DocumentsService(DocumentsRepository documentsRepository,
                            ApplicantRepository applicantRepository) {
        this.documentsRepository = documentsRepository;
        this.applicantRepository = applicantRepository;
    }

    private boolean isDocumentsInExcel(Tender tender, Applicant applicant) {
        Documents documentsFromDB = documentsRepository.findByTenderAndApplicant(tender, applicant);
        if (documentsFromDB != null) {
            return true;
        }
        return false;
    }

    public boolean isDocuments(Tender tender) {
        Iterable<Documents> documentsFromDB = documentsRepository.findByTender(tender);
        if (documentsFromDB.iterator().hasNext()) {
            return true;
        }
        return false;
    }

    public boolean addDocumentsFromExcel(Tender bufTender,
                                         ArrayList<Applicant> applicantArrayList) {
        Documents documents;
        for (Applicant bufApplicant : applicantArrayList) {
            if (isDocumentsInExcel(bufTender, applicantRepository.findByNameA(bufApplicant.getNameA()))) {
                return false;
            } else {
                documents = new Documents();
                documents.setTender(bufTender);
                documents.setApplicant(applicantRepository.findByNameA(bufApplicant.getNameA()));
                documentsRepository.save(documents);
            }
        }
        return true;
    }
}

