package com.holmech.tender.application.service;

import com.holmech.tender.application.entity.Applicant;
import com.holmech.tender.application.entity.Documents;
import com.holmech.tender.application.entity.Tender;
import com.holmech.tender.application.repository.ApplicantRepository;
import com.holmech.tender.application.repository.DocumentsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentsService {

    private final DocumentsRepository documentsRepository;
    private final ApplicantService applicantService;

    public DocumentsService(DocumentsRepository documentsRepository,
                            ApplicantService applicantService) {
        this.documentsRepository = documentsRepository;
        this.applicantService = applicantService;
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
            if (isDocumentsInExcel(bufTender, applicantService.findByNameA(bufApplicant.getNameA()))) {
                return false;
            } else {
                documents = new Documents();
                documents.setTender(bufTender);
                documents.setApplicant(applicantService.findByNameA(bufApplicant.getNameA()));
                documentsRepository.save(documents);
            }
        }
        return true;
    }

    public void updateDocumentsList(List<Documents> documents){
        for(Documents document: documents) {
            applicantService.updateApplicant(document.getApplicant());
        }
        documentsRepository.saveAll(documents);
    }
}

