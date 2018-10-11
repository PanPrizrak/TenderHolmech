package com.holmech.tender.application.controller;

import com.holmech.tender.application.entity.Documents;
import com.holmech.tender.application.entity.Subject;
import com.holmech.tender.application.entity.Tender;
import com.holmech.tender.application.excelparser.SubjectParseExcel;
import com.holmech.tender.application.form.SubjectAndDocumentsForm;
import com.holmech.tender.application.repository.DocumentsRepository;
import com.holmech.tender.application.repository.TenderRepository;
import com.holmech.tender.application.service.SubjectService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
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
    private final DocumentsRepository documentsRepository;

    public TenderController(TenderRepository tenderRepository,
                            SubjectService subjectService,
                            DocumentsRepository documentsRepository) {
        this.tenderRepository = tenderRepository;
        this.subjectService = subjectService;
        this.documentsRepository = documentsRepository;
    }

    @GetMapping("/tender/{numberT}")
    public ModelAndView get(@PathVariable String numberT) {
        Tender tenderFromDB = tenderRepository.findByNumberT(numberT);
        List<Subject> subjects = subjectService.findByTenderNumberT(tenderFromDB);
        List<Documents> documents = documentsRepository.findByTender(tenderFromDB);
        SubjectAndDocumentsForm subjectAndDocumentsForm = new SubjectAndDocumentsForm(subjects,documents);
        return new ModelAndView("tender", "subjectAndDocumentsForm", subjectAndDocumentsForm);
    }

    @PostMapping("/tender/{numberT}")
    public ModelAndView save(@PathVariable String numberT,
                             @ModelAttribute("subjectAndDocumentsForm") SubjectAndDocumentsForm subjectAndDocumentsForm,
                             @RequestParam(required = false, name = "file") MultipartFile file) throws IOException {
        String bufPath = uploadPath + tenderRepository.findByNumberT(numberT).getFilename();
        List<Subject> subjects = null;
        if (file.isEmpty()) {
            subjects = subjectAndDocumentsForm.getSubjectList();
        } else {
            file.transferTo(new File(new String(bufPath)));
            List<Subject> bufSubjectExcel = SubjectParseExcel.readFromExcel(new File(bufPath));
            subjects = subjectService.setApplicantInSubjectList(bufSubjectExcel, subjectAndDocumentsForm.getSubjectList());
        }
        if (null != subjects && subjects.size() > 0) {
            subjectService.updateSubjectList(subjects);
        }
        subjectAndDocumentsForm.setSubjectList((List<Subject>) subjects);
        return new ModelAndView("tender", "subjectAndDocumentsForm", subjectAndDocumentsForm);
    }
}
