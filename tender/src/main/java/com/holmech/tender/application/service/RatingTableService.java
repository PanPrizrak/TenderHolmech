package com.holmech.tender.application.service;

import com.holmech.tender.application.entity.Subject;
import com.holmech.tender.application.entity.SubjectAfterTheReduction;
import com.holmech.tender.application.entity.Tender;
import com.holmech.tender.application.entity.calculations.ObjT;
import com.holmech.tender.application.parser.fromexcel.RatingTableParserExcel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingTableService {

    private final RatingTableParserExcel ratingTableParserExcel;
    private final TenderService tenderService;
    private final SubjectService subjectService;
    private final SubjectAfterTheReductionService subjectAfterTheReductionService;

    public RatingTableService(TenderService tenderService,
                              SubjectService subjectService,
                              SubjectAfterTheReductionService subjectAfterTheReductionService,
                              RatingTableParserExcel ratingTableParserExcel) {
        this.tenderService = tenderService;
        this.subjectService = subjectService;
        this.subjectAfterTheReductionService = subjectAfterTheReductionService;
        this.ratingTableParserExcel = ratingTableParserExcel;
    }

    public void generateRatingTable(String numberT){
        Tender tenderFromDB = tenderService.findByNumberT(numberT);
        ratingTableParserExcel.parse(generateObjT(tenderFromDB),tenderFromDB);
    }

    private ArrayList<ObjT> generateObjT(Tender tenderFromDB) {
        ArrayList<ObjT> objTArrayList = new ArrayList<>();
        List<Subject> subjectList = subjectService.findByNumberT(tenderFromDB.getNumberT())
                .stream().sorted(Comparator.comparing(Subject::getNumberS)).collect(Collectors.toList());
        for(Subject subject: subjectList){
            ObjT objT = new ObjT();
            objT.setLot(subject.getNumberS());
            objT.setNameC(subject.getApplicant().getNameA());
            objT.setNameO(subject.getNameS());
            objT.setEd(subject.getUnits());
            objT.setCen(subject.getPrice().floatValue());

            SubjectAfterTheReduction subjectAfterTheReduction = subjectAfterTheReductionService.findBySubject(subject);
            if(subjectAfterTheReduction.getPrice() != 0.0) {
                objT.setOts(Integer.parseInt(subjectAfterTheReduction.getPayment()));
                objT.setCenS(subjectAfterTheReduction.getPrice().floatValue());
                objT.setCenO(objT.getCenS());
            } else {
                objT.setOts(Integer.parseInt(subject.getPayment()));
                objT.setCenS((float) 0.0);
                objT.setCenO(objT.getCen());
            }
            objTArrayList.add(objT);
        }
        return objTArrayList;
    }
}
