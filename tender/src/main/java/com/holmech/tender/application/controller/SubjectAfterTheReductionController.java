package com.holmech.tender.application.controller;


import com.holmech.tender.application.entity.Subject;
import com.holmech.tender.application.entity.SubjectAfterTheReduction;
import com.holmech.tender.application.form.SubjectAfterTheReductionForm;
import com.holmech.tender.application.parser.fromexcel.SubjectAfterTheReductionParseExcel;
import com.holmech.tender.application.parser.fromexcel.SubjectParseExcel;
import com.holmech.tender.application.service.SubjectAfterTheReductionService;
import com.holmech.tender.application.service.SubjectService;
import com.holmech.tender.application.service.TenderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class SubjectAfterTheReductionController {

    @Value("${upload.path}")
    private String uploadPath;

    private final SubjectAfterTheReductionService subjectAfterTheReductionService;
    private final SubjectService subjectService;
    private final TenderService tenderService;
    private final SubjectAfterTheReductionParseExcel subjectAfterTheReductionParseExcel;

    public SubjectAfterTheReductionController(SubjectAfterTheReductionService subjectAfterTheReductionService,
                                              SubjectService subjectService,
                                              TenderService tenderService,
                                              SubjectAfterTheReductionParseExcel subjectAfterTheReductionParseExcel) {
        this.subjectAfterTheReductionService = subjectAfterTheReductionService;
        this.subjectService = subjectService;
        this.tenderService = tenderService;
        this.subjectAfterTheReductionParseExcel = subjectAfterTheReductionParseExcel;
    }

    @GetMapping("/satrintender/{numberT}")
    public ModelAndView get(@PathVariable String numberT) {

        return new ModelAndView("satrintender", "subjectAfterTheReductionForm", getSubjectAfterTheReductionForm(numberT));

    }

    @PostMapping("/satrintender/{numberT}")
    public ModelAndView saveSubjectAfterTheReducation(@PathVariable String numberT,
                                                      @ModelAttribute("subjectAfterTheReductionForm") SubjectAfterTheReductionForm subjectAfterTheReductionForm,
                                                      @RequestParam(required = false, name = "file") MultipartFile file
                                                    //,@RequestParam(required = false) boolean refreshSubjectList
    ) throws IOException {
        String bufPath = uploadPath + tenderService.findByNumberT(numberT).getFilename();
        List<SubjectAfterTheReduction> subjectAfterTheReductionList = null;
        if (file != null) {
            if (file.isEmpty()) {
                subjectAfterTheReductionList = subjectAfterTheReductionForm.getSubjectAfterTheReductionList();
            } else {
                file.transferTo(new File(bufPath));
                List<SubjectAfterTheReduction> bufSubjectAfterTheReductionExcel = subjectAfterTheReductionParseExcel.readFromExcel(new File(bufPath));
                /*if (refreshSubjectList) {
                    subjectService.removeTheSubjectsFromTheTender(subjectAndDocumentsForm.getSubjectList());
                    for(Subject sub:bufSubjectExcel){
                        sub.setTenderNumberT(numberT);
                    }
                    subjectAfterTheReductionList = bufSubjectExcel;
                } */
                subjectAfterTheReductionList = bufSubjectAfterTheReductionExcel;
                }
            }

        subjectAfterTheReductionService.saveAll(subjectAfterTheReductionList);

        return new ModelAndView("satrintender", "subjectAfterTheReductionForm", getSubjectAfterTheReductionForm(numberT));
    }

    private SubjectAfterTheReductionForm getSubjectAfterTheReductionForm(String numberT) {
        List<Subject> subjectList = subjectService.getMeetSubject(tenderService.findByNumberT(numberT));
        List<SubjectAfterTheReduction> subjectAfterTheReductionList = subjectAfterTheReductionService.findBySubjectIn(subjectList);
        if (subjectAfterTheReductionList != null && subjectAfterTheReductionList.size() != 0) {
            return new SubjectAfterTheReductionForm(subjectAfterTheReductionList);
        } else {
            subjectAfterTheReductionList.clear();
            subjectAfterTheReductionService.emptySubjectAfterTheReduction(subjectList, subjectAfterTheReductionList);
            return new SubjectAfterTheReductionForm(subjectAfterTheReductionList);
        }
    }



}
