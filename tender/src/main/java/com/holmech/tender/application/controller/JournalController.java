package com.holmech.tender.application.controller;

import com.holmech.tender.application.entity.Tender;
import com.holmech.tender.application.form.TenderForm;
import com.holmech.tender.application.service.TenderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("journal")
public class JournalController {

    private final TenderService tenderService;

    public JournalController(TenderService tenderService) {
        this.tenderService = tenderService;
    }

    @GetMapping()
    public ModelAndView journal() {
        List<Tender> tenders;
        tenders = tenderService.findAll();
        return new ModelAndView("journal", "tenderForm", new TenderForm(tenders));
    }

    @PostMapping()
    public ModelAndView add(
            @ModelAttribute("tenderForm") TenderForm tenderForm,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        Tender tenderBuf = tenderForm.getTenderList().get(0);
        tenderService.saveTender(file, tenderBuf);
        List<Tender> tenders = tenderService.findAll();
        TenderForm tenderBufForm = new TenderForm(tenders);
        return new ModelAndView("journal", "tenderForm", tenderBufForm);
    }

}//JournalController
