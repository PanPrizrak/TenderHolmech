package com.holmech.tender.application.controller;

import com.holmech.tender.application.entity.Subject;
import com.holmech.tender.application.entity.Tender;
import com.holmech.tender.application.form.SubjectForm;
import com.holmech.tender.application.repository.SubjectRepository;
import com.holmech.tender.application.repository.TenderRepository;
import com.holmech.tender.application.service.SubjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TenderController {

    private final TenderRepository tenderRepository;
    private final SubjectService subjectService;

    public TenderController(TenderRepository tenderRepository, SubjectService subjectService) {
        this.tenderRepository = tenderRepository;
        this.subjectService = subjectService;
    }

    @GetMapping("/tender/{numberT}")
    public ModelAndView get(@PathVariable String numberT) {
        Tender tenderFromDB = tenderRepository.findByNumberT(numberT);
        List<Subject> subjects = subjectService.findByTenderNumberT(tenderFromDB);
        SubjectForm subjectForm = new SubjectForm();
        subjectForm.setSubjectList((List<Subject>) subjects);
        return new ModelAndView("tender" , "subjectForm", subjectForm);
    }

    @PostMapping("/tender/{numberT}")
    public ModelAndView save(@PathVariable String numberT,
                             @ModelAttribute("subjectForm") SubjectForm subjectForm,
                             @RequestParam(required = false, name = "file") MultipartFile file) {
        System.out.println(subjectForm);
        System.out.println(subjectForm.getSubjectList());
        List<Subject> subjects = subjectForm.getSubjectList();
        if(null != subjects && subjects.size() > 0) {
           // subjectService.updateSubjectList(subjects);
            for (Subject subject : subjects) {
                System.out.println("!!!!!!!!!!!!!!!!!!Iter subjects == " + subject.toString());//"%s \t %s \n", contact.getFirstname(), contact.getLastname()
            }
        }

        return new ModelAndView("tender" , "subjectForm", subjectForm);
    }
}
