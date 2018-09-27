package com.holmech.tender.application.service;

import com.holmech.tender.application.entity.Applicant;
import com.holmech.tender.application.entity.Subject;
import com.holmech.tender.application.entity.Tender;
import com.holmech.tender.application.excelparser.ApplicantParseExcel;
import com.holmech.tender.application.repository.ApplicantRepository;
import com.holmech.tender.application.repository.SubjectRepository;
import com.holmech.tender.application.repository.TenderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    @Value("${upload.path}")
    private String uploadPath;

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
                                    ArrayList<Applicant> applicantArrayList) throws IOException {
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
        ApplicantParseExcel.saveInExcel(findByTenderNumberT(bufTender.get()), new File(new String(uploadPath + bufTender.get().getFilename())));
    }

    public List<Subject> findByTenderNumberT(Tender tenderFromDB){
        return subjectRepository.findByTender(tenderFromDB,Sort.by("id").ascending());
    }

    public void updateSubjectList(List<Subject> subjectList) throws IOException {
        Subject subject;
        Tender bufTender = null;
        for (Subject subjects : subjectList) {
            subject = new Subject();
            subjects.setApplicant(applicantRepository.findByNameA(subjects.getApplicantNameA()));
            bufTender = tenderRepository.findByNumberT(subjects.getTenderNumberT());
            subjects.setTender(bufTender);
            subject = subjects;
            subjectRepository.save(subject);
        }
        ApplicantParseExcel.saveInExcel(findByTenderNumberT(bufTender), new File(new String(uploadPath + bufTender.getFilename())));
    }
}
