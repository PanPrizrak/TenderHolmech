package com.holmech.tender.application.service;

import com.holmech.tender.application.entity.Tender;
import com.holmech.tender.application.parser.fromexcel.RatingTableParserExcel;
import org.springframework.stereotype.Service;

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
        ratingTableParserExcel.parse(generate);
    }
}
