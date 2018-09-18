package com.holmech.tender.application.service;

import com.holmech.tender.application.entity.Applicant;
import com.holmech.tender.application.entity.Documents;
import com.holmech.tender.application.entity.Tender;
import com.holmech.tender.application.repository.DocumentsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class DocumentsService {

    private final DocumentsRepository documentsRepository;

    public DocumentsService(DocumentsRepository documentsRepository) {
        this.documentsRepository = documentsRepository;
    }

    public void addDocumentsFromExcel(Optional<Tender> bufTender,
                                      ArrayList<Applicant> applicantArrayList) {
        Documents documents = new Documents();
        for (Applicant bufApplicant : applicantArrayList) {
            for (int i = 0; i < bufApplicant.getLots().size(); i++) {
                int bufQuantityLots = bufApplicant.getLots().get(i);
                if (bufQuantityLots != 0) {
                    for (int j = 0; j <= bufQuantityLots; j++) {
                        documents.setTender(bufTender.get());
                        documents.setApplicant(bufApplicant);
                        documentsRepository.save(documents);
                    }
                }
            }

        }
    }
}
