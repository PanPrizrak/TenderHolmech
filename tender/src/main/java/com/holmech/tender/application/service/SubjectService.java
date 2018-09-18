package com.holmech.tender.application.service;

import com.holmech.tender.application.entity.Applicant;
import com.holmech.tender.application.entity.Subject;
import com.holmech.tender.application.entity.Tender;
import com.holmech.tender.application.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public void addSubjectFromExcel(Optional<Tender> bufTender,
                                     ArrayList<Applicant> applicantArrayList) {
        Subject subject = new Subject();
        for (Applicant bufApplicant : applicantArrayList) {
            for (int i = 0; i < bufApplicant.getLots().size(); i++) {
                int bufQuantityLots = bufApplicant.getLots().get(i);
                if (bufQuantityLots != 0) {
                    for (int j = 0; j <= bufQuantityLots; j++) {
                        subject.setNumberS(i + 1);
                        subject.setTender(bufTender.get());
                        subject.setApplicant(bufApplicant);
                        subjectRepository.save(subject);
                    }
                }
            }

        }
    }
}
