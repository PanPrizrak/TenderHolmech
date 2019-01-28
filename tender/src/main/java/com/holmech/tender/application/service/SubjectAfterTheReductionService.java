package com.holmech.tender.application.service;

import com.holmech.tender.application.entity.Subject;
import com.holmech.tender.application.entity.SubjectAfterTheReduction;
import com.holmech.tender.application.entity.Tender;
import com.holmech.tender.application.form.SubjectAfterTheReductionForm;
import com.holmech.tender.application.parser.fromexcel.ParseExcel;
import com.holmech.tender.application.parser.fromexcel.SubjectAfterTheReductionParseExcel;
import com.holmech.tender.application.parser.fromexcel.Write;
import com.holmech.tender.application.repository.SubjectAfterTheReductionRepository;
import com.holmech.tender.application.utilities.PathFromOS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectAfterTheReductionService {

    @Value("${upload.path}")
    private String uploadPath;

    private final SubjectAfterTheReductionRepository subjectAfterTheReductionRepository;
    private final SubjectAfterTheReductionParseExcel subjectAfterTheReductionParseExcel;
    private final SubjectService subjectService;
    private final TenderService tenderService;

    public SubjectAfterTheReductionService(SubjectAfterTheReductionRepository subjectAfterTheReductionRepository,
                                           SubjectService subjectService,
                                           TenderService tenderService,
                                           SubjectAfterTheReductionParseExcel subjectAfterTheReductionParseExcel) {
        this.subjectAfterTheReductionRepository = subjectAfterTheReductionRepository;
        this.subjectService = subjectService;
        this.tenderService = tenderService;
        this.subjectAfterTheReductionParseExcel = subjectAfterTheReductionParseExcel;
    }

    public SubjectAfterTheReduction findBySubject(Subject subject){
        return subjectAfterTheReductionRepository.findBySubject(subject);
    }

    public List<SubjectAfterTheReduction> findBySubjectIn(List<Subject> subjectList){
        return subjectAfterTheReductionRepository.findBySubjectIn(subjectList);
    }

    public void saveAll(List<SubjectAfterTheReduction> subjectAfterTheReductionList) {
        subjectAfterTheReductionRepository.saveAll( (Iterable<SubjectAfterTheReduction>) subjectAfterTheReductionList);
    }

    public void writeFromExcel(String numberT,List<SubjectAfterTheReduction> bufSubjecatAfterTheReduction){
        Tender tenderFromDB = tenderService.findByNumberT(numberT);
        List<Subject> subjectList = subjectService.getMeetSubject(tenderFromDB);
        List<SubjectAfterTheReduction> subjectAfterTheReductionList = new ArrayList<>();
        if(bufSubjecatAfterTheReduction != null){
            subjectAfterTheReductionList = bufSubjecatAfterTheReduction;
        } else {
            emptySubjectAfterTheReduction(subjectList, subjectAfterTheReductionList);
        }
        subjectAfterTheReductionParseExcel.saveInExcel(subjectAfterTheReductionList,new File(uploadPath +
                tenderFromDB.getNumberT() + PathFromOS.getPath() + tenderFromDB.getFilename()));
    }

    public void emptySubjectAfterTheReduction(List<Subject> subjectList, List<SubjectAfterTheReduction> subjectAfterTheReductionList) {
        for (Subject subject : subjectList) {
            SubjectAfterTheReduction subjectAfterTheReduction = new SubjectAfterTheReduction();
            subjectAfterTheReduction.setPrice(0.0);
            subjectAfterTheReduction.setPayment(subject.getPayment());
            subjectAfterTheReduction.setSubject(subject);
            subjectAfterTheReductionList.add(subjectAfterTheReduction);
        }
    }

    public void addSubjectInSATR(SubjectAfterTheReductionForm subjectAfterTheReductionForm) {
        for(SubjectAfterTheReduction subjectAfterTheReduction:subjectAfterTheReductionForm.getSubjectAfterTheReductionList()){
            subjectAfterTheReduction.setSubject(subjectService.findById(subjectAfterTheReduction.getSubject().getIdS()));
        }
    }
}
