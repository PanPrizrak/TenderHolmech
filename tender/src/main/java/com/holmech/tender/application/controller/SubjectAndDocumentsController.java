package com.holmech.tender.application.controller;

import com.holmech.tender.application.entity.Documents;
import com.holmech.tender.application.entity.Subject;
import com.holmech.tender.application.entity.Tender;
import com.holmech.tender.application.parser.fromexcel.SubjectParseExcel;
import com.holmech.tender.application.form.SubjectAndDocumentsForm;
import com.holmech.tender.application.repository.DocumentsRepository;
import com.holmech.tender.application.repository.TenderRepository;
import com.holmech.tender.application.service.DocumentsService;
import com.holmech.tender.application.service.RatingTableService;
import com.holmech.tender.application.service.SubjectService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class SubjectAndDocumentsController {

    @Value("${upload.path}")
    private String uploadPath;

    private final TenderRepository tenderRepository;
    private final SubjectService subjectService;
    private final DocumentsRepository documentsRepository;
    private final DocumentsService documentsService;
    private final RatingTableService ratingTableService;

    public SubjectAndDocumentsController(TenderRepository tenderRepository,
                                         SubjectService subjectService,
                                         DocumentsRepository documentsRepository,
                                         DocumentsService documentsService,
                                         RatingTableService ratingTableService) {
        this.tenderRepository = tenderRepository;
        this.subjectService = subjectService;
        this.documentsRepository = documentsRepository;
        this.documentsService = documentsService;
        this.ratingTableService = ratingTableService;
    }

    @GetMapping("/sad/{numberT}")
    public ModelAndView get(@PathVariable String numberT) {
        return new ModelAndView("sad", "subjectAndDocumentsForm", getSubjectAndDocumentsForm(numberT));
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        // this will allow 500 size of array.
        dataBinder.setAutoGrowCollectionLimit(10000);
    }


    @PostMapping("/sad/{numberT}")
    public ModelAndView save(@PathVariable String numberT,
                             @ModelAttribute("subjectAndDocumentsForm") SubjectAndDocumentsForm subjectAndDocumentsForm,
                             @RequestParam(required = false, name = "file") MultipartFile file,
                             @RequestParam(required = false) boolean refreshSubjectList) throws IOException {
        String bufPath = uploadPath + tenderRepository.findByNumberT(numberT).getFilename();
        List<Subject> subjects = null;
        if (file != null) {
            if (file.isEmpty()) {
                subjects = subjectAndDocumentsForm.getSubjectList();
            } else {
                file.transferTo(new File(new String(bufPath)));
                List<Subject> bufSubjectExcel = SubjectParseExcel.readFromExcel(new File(bufPath));
                if (refreshSubjectList) {
                    subjectService.removeTheSubjectsFromTheTender(subjectAndDocumentsForm.getSubjectList());
                    for(Subject sub:bufSubjectExcel){
                        sub.setTenderNumberT(numberT);
                    }
                    subjects = bufSubjectExcel;
                } else {

                    subjects = subjectService.setApplicantInSubjectList(bufSubjectExcel, subjectAndDocumentsForm.getSubjectList());
                }
            }
        }
        if (null != subjects && subjects.size() > 0) {
            subjectService.updateSubjectList(subjects);
        }
        if (subjectAndDocumentsForm.getDocumentsList() != null) {
            documentsService.updateDocumentsList(subjectAndDocumentsForm.getDocumentsList());
        }
        ratingTableService.generateRatingTable(numberT);
        return new ModelAndView("sad", "subjectAndDocumentsForm", getSubjectAndDocumentsForm(numberT));
    }

    @org.jetbrains.annotations.NotNull
    private SubjectAndDocumentsForm getSubjectAndDocumentsForm(@PathVariable String numberT) {
        Tender tenderFromDB = tenderRepository.findByNumberT(numberT);
        List<Subject> subjects = subjectService.findByTenderSortNumberT(tenderFromDB);
        List<Documents> documents = documentsRepository.findByTender(tenderFromDB);
        return new SubjectAndDocumentsForm(subjects, documents);
    }
}
