package com.holmech.tender.application.service;

import com.holmech.tender.application.entity.Applicant;
import com.holmech.tender.application.entity.Subject;
import com.holmech.tender.application.entity.Tender;
import com.holmech.tender.application.parser.fromexcel.SubjectParseExcel;
import com.holmech.tender.application.repository.SubjectRepository;
import com.holmech.tender.application.repository.TenderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectService {

    @Value("${upload.path}")
    private String uploadPath;

    private final SubjectRepository subjectRepository;
    private final ApplicantService applicantService;
    private final TenderRepository tenderRepository;


    public SubjectService(SubjectRepository subjectRepository,
                          ApplicantService applicantService,
                          TenderRepository tenderRepository) {
        this.subjectRepository = subjectRepository;
        this.applicantService = applicantService;
        this.tenderRepository = tenderRepository;
    }

    public void addSubjectFromExcel(Tender bufTender,
                                    ArrayList<Applicant> applicantArrayList) throws IOException {
        Subject subject;
        for (Applicant bufApplicant : applicantArrayList) {
            for (int i = 0; i < bufApplicant.getLots().size(); i++) {// определение количества лотов претендента
                int bufQuantityLots = bufApplicant.getLots().get(i);//определение количества предложений по лоту i
                if (bufQuantityLots > 0) {
                    for (int j = 0; j < bufQuantityLots; j++) {
                        subject = new Subject();
                        subject.setNumberS(i + 1);
                        subject.setTender(bufTender);
                        subject.setApplicant(applicantService.findByNameA(bufApplicant.getNameA()));
                        subjectRepository.save(subject);
                    }
                }
            }
        }
        SubjectParseExcel.saveInExcel(findByTenderNumberT(bufTender), new File(new String(uploadPath + bufTender.getFilename())));
    }

    public List<Subject> findByTenderNumberT(Tender tenderFromDB) {
        return subjectRepository.findByTender(tenderFromDB, Sort.by("idS").ascending());
    }

    public void updateSubjectList(List<Subject> subjectList) throws IOException {
        Subject subject;
        Tender bufTender = null;
        for (Subject subjects : subjectList) {
            subject = new Subject();
            subjects.setApplicant(applicantService.findByNameA(subjects.getApplicantNameA()));
            bufTender = tenderRepository.findByNumberT(subjects.getTenderNumberT());
            subjects.setTender(bufTender);
            subject = subjects;
            subjectRepository.save(subject);
        }
        SubjectParseExcel.saveInExcel(findByTenderNumberT(bufTender), new File(new String(uploadPath + bufTender.getFilename())));
    }

    public List<Subject> setApplicantInSubjectList(List<Subject> subjectList, List<Subject> subjectListWithId) throws IOException {
        ArrayList<Subject> bufSubjectList = new ArrayList<Subject>();
        for (int i = 0; i < subjectList.size(); i++) {
            Subject bufSubject = subjectList.get(i);
            bufSubject.setIdS(subjectListWithId.get(i).getIdS());
            bufSubject.setTenderNumberT(subjectListWithId.get(i).getTenderNumberT());
            bufSubjectList.add(bufSubject);
        }
        return bufSubjectList;
    }
}
