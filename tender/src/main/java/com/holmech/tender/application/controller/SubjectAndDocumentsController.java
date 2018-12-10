package com.holmech.tender.application.controller;

import com.holmech.tender.application.entity.Documents;
import com.holmech.tender.application.entity.Subject;
import com.holmech.tender.application.entity.Tender;
import com.holmech.tender.application.parser.fromexcel.SubjectParseExcel;
import com.holmech.tender.application.form.SubjectAndDocumentsForm;
import com.holmech.tender.application.repository.DocumentsRepository;
import com.holmech.tender.application.repository.TenderRepository;
import com.holmech.tender.application.service.DocumentsService;
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
public class SubjectAndDocumentsController {

    @Value("${upload.path}")
    private String uploadPath;

    private final TenderRepository tenderRepository;
    private final SubjectService subjectService;
    private final DocumentsRepository documentsRepository;
    private final DocumentsService documentsService;

    public SubjectAndDocumentsController(TenderRepository tenderRepository,
                                         SubjectService subjectService,
                                         DocumentsRepository documentsRepository,
                                         DocumentsService documentsService) {
        this.tenderRepository = tenderRepository;
        this.subjectService = subjectService;
        this.documentsRepository = documentsRepository;
        this.documentsService = documentsService;
    }

    @GetMapping("/sad/{numberT}")
    public ModelAndView get(@PathVariable String numberT) {
        return new ModelAndView("sad", "subjectAndDocumentsForm", getSubjectAndDocumentsForm(numberT));
    }



    @PostMapping("/sad/{numberT}")
    public ModelAndView save(@PathVariable String numberT,
                             @ModelAttribute("subjectAndDocumentsForm") SubjectAndDocumentsForm subjectAndDocumentsForm,
                             @RequestParam(required = false, name = "file") MultipartFile file) throws IOException {
        String bufPath = uploadPath + tenderRepository.findByNumberT(numberT).getFilename();
        List<Subject> subjects = null;
        if(file != null) {
            if (file.isEmpty()) {
                subjects = subjectAndDocumentsForm.getSubjectList();
            } else {
                file.transferTo(new File(new String(bufPath)));
                List<Subject> bufSubjectExcel = SubjectParseExcel.readFromExcel(new File(bufPath));
                subjects = subjectService.setApplicantInSubjectList(bufSubjectExcel, subjectAndDocumentsForm.getSubjectList());
            }
        }
        if (null != subjects && subjects.size() > 0) {
            subjectService.updateSubjectList(subjects);
        }
        if(subjectAndDocumentsForm.getDocumentsList() != null) {
            documentsService.updateDocumentsList(subjectAndDocumentsForm.getDocumentsList());
        }
        return new ModelAndView("sad", "subjectAndDocumentsForm", getSubjectAndDocumentsForm(numberT));
    }

    @org.jetbrains.annotations.NotNull
    private SubjectAndDocumentsForm getSubjectAndDocumentsForm(@PathVariable String numberT) {
        Tender tenderFromDB = tenderRepository.findByNumberT(numberT);
        List<Subject> subjects =  subjectService.findByTenderNumberT(tenderFromDB).subList(0,120);//todo
        List<Documents> documents =  documentsRepository.findByTender(tenderFromDB);
        return new SubjectAndDocumentsForm(subjects,documents);
    }
}
