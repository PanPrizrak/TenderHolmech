package com.holmech.tender.application.controller;

import com.holmech.tender.application.entity.Subject;
import com.holmech.tender.application.entity.Tender;
import com.holmech.tender.application.excelparser.SubjectParseExcel;
import com.holmech.tender.application.form.SubjectForm;
import com.holmech.tender.application.repository.SubjectRepository;
import com.holmech.tender.application.repository.TenderRepository;
import com.holmech.tender.application.service.SubjectService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TenderController {

    @Value("${upload.path}")
    private String uploadPath;

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
        return new ModelAndView("tender", "subjectForm", subjectForm);
    }

    @PostMapping("/tender/{numberT}")
    public ModelAndView save(@PathVariable String numberT,
                             @ModelAttribute("subjectForm") SubjectForm subjectForm,
                             @RequestParam(required = false, name = "file") MultipartFile file) throws IOException {
        String bufPath = uploadPath + "\\" + tenderRepository.findByNumberT(numberT).getFilename();
        List<Subject> subjects = null;
        if (file.isEmpty()) {
            subjects = subjectForm.getSubjectList();
        } else {
            Tender tenderFromDB = tenderRepository.findByNumberT(numberT);
            file.transferTo(new File(new String(bufPath)));
            subjects = SubjectParseExcel.readFromExcel(new File(bufPath), tenderFromDB);
        }
        if (null != subjects && subjects.size() > 0) {
            subjectService.updateSubjectList(subjects);
        }
        subjectForm.setSubjectList((List<Subject>) subjects);
        return new ModelAndView("tender", "subjectForm", subjectForm);
    }
}
