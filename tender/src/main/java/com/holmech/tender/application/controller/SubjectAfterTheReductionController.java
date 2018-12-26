package com.holmech.tender.application.controller;


import com.holmech.tender.application.entity.Subject;
import com.holmech.tender.application.entity.SubjectAfterTheReduction;
import com.holmech.tender.application.form.SubjectAfterTheReductionForm;
import com.holmech.tender.application.service.SubjectAfterTheReductionService;
import com.holmech.tender.application.service.SubjectService;
import com.holmech.tender.application.service.TenderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class SubjectAfterTheReductionController {

    private final SubjectAfterTheReductionService subjectAfterTheReductionService;
    private final SubjectService subjectService;
    private final TenderService tenderService;

    public SubjectAfterTheReductionController(SubjectAfterTheReductionService subjectAfterTheReductionService,
                                              SubjectService subjectService,
                                              TenderService tenderService) {
        this.subjectAfterTheReductionService = subjectAfterTheReductionService;
        this.subjectService = subjectService;
        this.tenderService = tenderService;
    }

    @GetMapping("/satrintender/{numberT}")
    public ModelAndView get(@PathVariable String numberT) {

        return new ModelAndView("satrintender", "subjectAfterTheReductionForm", getSubjectAfterTheReductionForm(numberT));

    }

    @PostMapping("/satrintender/{numberT}")
    public ModelAndView saveSubjectAfterTheReducation(@PathVariable String numberT,
                                                      @ModelAttribute("subjectAfterTheReductionForm") SubjectAfterTheReductionForm subjectAfterTheReductionForm) {
        subjectAfterTheReductionService.saveAll(subjectAfterTheReductionForm.getSubjectAfterTheReductionList());

        return new ModelAndView("satrintender", "subjectAfterTheReductionForm", getSubjectAfterTheReductionForm(numberT));
    }

    private SubjectAfterTheReductionForm getSubjectAfterTheReductionForm(String numberT) {
        List<Subject> subjectList = subjectService.getMeetSubject(tenderService.findByNumberT(numberT));
        List<SubjectAfterTheReduction> subjectAfterTheReductionList = subjectAfterTheReductionService.findBySubjectIn(subjectList);
        if (subjectAfterTheReductionList != null && subjectAfterTheReductionList.size() != 0) {
            return new SubjectAfterTheReductionForm(subjectAfterTheReductionList);
        } else {
            subjectAfterTheReductionList.clear();
            for (Subject subject : subjectList) {
                SubjectAfterTheReduction subjectAfterTheReduction = new SubjectAfterTheReduction();
                subjectAfterTheReduction.setPrice(0.0);
                subjectAfterTheReduction.setPayment(subject.getPayment());
                subjectAfterTheReduction.setSubject(subject);
                subjectAfterTheReductionList.add(subjectAfterTheReduction);
            }
            return new SubjectAfterTheReductionForm(subjectAfterTheReductionList);
        }
    }

}
