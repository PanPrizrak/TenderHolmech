package com.holmech.tender.application.controller;

import com.holmech.tender.application.entity.Order;
import com.holmech.tender.application.entity.Tender;
import com.holmech.tender.application.form.TenderForm;
import com.holmech.tender.application.repository.TenderRepository;
import com.holmech.tender.application.service.TenderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EditTenderController {

    private final TenderService tenderService;


    public EditTenderController(TenderService tenderService) {
        this.tenderService = tenderService;
    }

    @GetMapping("/editTender/{numberT}")
    private ModelAndView editTender(@PathVariable String numberT)
    {
        Tender bufTender = tenderService.findByNumberT(numberT);
        ArrayList<Tender> tenferBufList = new ArrayList<>();
        tenferBufList.add(bufTender);
        TenderForm tenderForm = new TenderForm(tenferBufList);
        return new ModelAndView("editTender","tenderForm", tenderForm);
    }

    @PostMapping("/editTender/{numberT}")
    private String editTender(@PathVariable String numberT,

                            @RequestParam(required = false, name = "file") MultipartFile file
    ) throws IOException {
        Tender bufTender = tenderService.findByNumberT(numberT);

        return "editTender";
    }
}
