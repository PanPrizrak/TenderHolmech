package com.holmech.tender.application.controller;

import com.holmech.tender.application.entity.Applicant;
import com.holmech.tender.application.entity.Order;
import com.holmech.tender.application.entity.Tender;
import com.holmech.tender.application.excelparser.ApplicantParseExcel;
import com.holmech.tender.application.form.TenderForm;
import com.holmech.tender.application.repository.OrderRepository;
import com.holmech.tender.application.repository.TenderRepository;
import com.holmech.tender.application.service.ApplicantService;
import com.holmech.tender.application.service.DocumentsService;
import com.holmech.tender.application.service.SubjectService;
import com.holmech.tender.application.service.TenderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.*;


@Controller
@RequestMapping("journal")
public class JournalController {

    //initial in lombook anatation @AllArgsConstructor
    private final ApplicantService applicantService;
    private final DocumentsService documentsService;
    private final SubjectService subjectService;
    private final TenderService tenderService;

    public JournalController(ApplicantService applicantService,
                             DocumentsService documentsService,
                             SubjectService subjectService,
                             TenderService tenderService) {
        this.applicantService = applicantService;
        this.documentsService = documentsService;
        this.subjectService = subjectService;
        this.tenderService = tenderService;
    }

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping()
    public ModelAndView journal() {
        List<Tender> tenders;
        tenders = tenderService.findAll();
        return new ModelAndView("journal", "tenderForm", new TenderForm(tenders));
    }

    private Tender getLastTender() {
        List<Tender> tenderList = tenderService.findAll();
        return tenderList.get(tenderList.size() - 1);
    }

    @PostMapping()
    public ModelAndView add(
            @RequestParam(required = false, name = "idtender") Long idtender,
            @ModelAttribute("tenderForm") TenderForm tenderForm,
            @RequestParam(required = false, name = "file") MultipartFile file
    ) throws IOException {

        if (idtender != null) {
            Tender bufTender = tenderService.findById(idtender);
            String bufPath = new String(uploadPath + "/" + bufTender.getFilename());// for win '/' as '\\'
            if (!documentsService.isDocuments(bufTender)) {
                ArrayList<Applicant> applicantArrayList = ApplicantParseExcel.parse(new File(bufPath));
                applicantService.addApplicants(applicantArrayList);//save applicants
                if (documentsService.addDocumentsFromExcel(bufTender, applicantArrayList)) {//save in documents
                    subjectService.addSubjectFromExcel(bufTender, applicantArrayList);
                }
            }
        } else {

            Tender tenderBuf = tenderForm.getTenderList().get(0);
            tenderService.saveTender(file, tenderBuf);
        }
        List<Tender> tenders =  tenderService.findAll();
        TenderForm tenderBufForm = new TenderForm(tenders);
        return new ModelAndView("journal", "tenderForm", tenderBufForm);
    }


}//JournalController
