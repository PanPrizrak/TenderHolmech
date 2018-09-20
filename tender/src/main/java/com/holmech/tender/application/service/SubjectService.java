package com.holmech.tender.application.service;

import com.holmech.tender.application.entity.Applicant;
import com.holmech.tender.application.entity.Subject;
import com.holmech.tender.application.entity.Tender;
import com.holmech.tender.application.repository.ApplicantRepository;
import com.holmech.tender.application.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final ApplicantRepository applicantRepository;

    public SubjectService(SubjectRepository subjectRepository,
                          ApplicantRepository applicantRepository) {
        this.subjectRepository = subjectRepository;
        this.applicantRepository = applicantRepository;
    }

    public void addSubjectFromExcel(Optional<Tender> bufTender,
                                     ArrayList<Applicant> applicantArrayList) {
        Subject subject;
        for (Applicant bufApplicant : applicantArrayList) {
            for (int i = 0; i < bufApplicant.getLots().size(); i++) {// определение количества лотов претендента
                int bufQuantityLots = bufApplicant.getLots().get(i);//определение количества предложений по лоту i
                if (bufQuantityLots > 0) {
                    for (int j = 0; j < bufQuantityLots; j++) {
                        subject = new Subject();
                        subject.setNumberS(i + 1);
                        subject.setTender(bufTender.get());
                        subject.setApplicant(applicantRepository.findByNameA(bufApplicant.getNameA()));
                        subjectRepository.save(subject);
                    }
                }
            }

        }
    }
}
