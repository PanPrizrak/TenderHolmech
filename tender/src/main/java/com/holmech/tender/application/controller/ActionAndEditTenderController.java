package com.holmech.tender.application.controller;

import com.holmech.tender.application.entity.Order;
import com.holmech.tender.application.entity.Tender;
import com.holmech.tender.application.form.TenderForm;
import com.holmech.tender.application.repository.TenderRepository;
import com.holmech.tender.application.service.TenderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ActionAndEditTenderController {

    private final TenderService tenderService;


    public ActionAndEditTenderController(TenderService tenderService) {
        this.tenderService = tenderService;
    }

    @GetMapping("/tender/{numberT}")
    private ModelAndView editTender(@PathVariable String numberT) {
        Tender bufTender = tenderService.findByNumberT(numberT);
        ArrayList<Tender> tenderBufList = new ArrayList<>();
        tenderBufList.add(bufTender);
        TenderForm tenderForm = new TenderForm(tenderBufList);
        return new ModelAndView("tender", "tenderForm", tenderForm);
    }

    @PostMapping("/tender/{numberT}")
    private ModelAndView editTender(@PathVariable String numberT,
                                    @RequestParam(required = false, defaultValue = "") String action,
                                    @ModelAttribute("tenderForm") TenderForm tenderForm,
                                    @RequestParam(required = false, name = "file") MultipartFile file
    ) throws IOException {
        if(action.isEmpty()) {
            Tender tenderBuf = tenderForm.getTenderList().get(0);
            tenderBuf.setIdT(tenderService.findByNumberT(numberT).getIdT());
            tenderService.saveTender(file, tenderBuf);
        } else {
            switch (action){
                case "Generate autopsy protocol":{
                    break;
                }
                case "Invite members to the price reduction procedure":{
                    break;
                }
                case "Form a decision protocol":{
                 break;
                }
                case "Notify participants about the results":{
                    break;
                }
            }
        }
        List<Tender> tenders = tenderService.findAll();
        TenderForm tenderBufForm = new TenderForm(tenders);
        return new ModelAndView("tender", "tenderForm", tenderBufForm);
    }
}
