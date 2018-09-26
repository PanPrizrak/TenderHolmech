package com.holmech.tender.application.service;

import com.holmech.tender.application.entity.Applicant;
import com.holmech.tender.application.entity.Subject;
import com.holmech.tender.application.entity.Tender;
import com.holmech.tender.application.repository.ApplicantRepository;
import com.holmech.tender.application.repository.SubjectRepository;
import com.holmech.tender.application.repository.TenderRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final ApplicantRepository applicantRepository;
    private final TenderRepository tenderRepository;

    public SubjectService(SubjectRepository subjectRepository,
                          ApplicantRepository applicantRepository,
                          TenderRepository tenderRepository) {
        this.subjectRepository = subjectRepository;
        this.applicantRepository = applicantRepository;
        this.tenderRepository = tenderRepository;
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

    public List<Subject> findByTenderNumberT(Tender tenderFromDB){
        return subjectRepository.findByTender(tenderFromDB,Sort.by("id").ascending());
    }

    public void updateSubjectList(List<Subject> subjectList) {
        Subject subject;
        for (Subject subjects : subjectList) {
            subject = new Subject();
            subjects.setApplicant(applicantRepository.findByNameA(subjects.getApplicantNameA()));
            subjects.setTender(tenderRepository.findByNumberT(subjects.getTenderNumberT()));
            subject = subjects;
            subjectRepository.save(subject);
        }
    }
}
