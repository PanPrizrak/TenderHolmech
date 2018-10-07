package com.holmech.tender.application.controller;

import com.holmech.tender.application.entity.Order;
import com.holmech.tender.application.entity.Tender;
import com.holmech.tender.application.form.TenderForm;
import com.holmech.tender.application.repository.TenderRepository;
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

@Controller
public class EditTenderController {

    private final TenderRepository tenderRepository;


    public EditTenderController(TenderRepository tenderRepository) {
        this.tenderRepository = tenderRepository;
    }

    @GetMapping("/editTender/{numberT}")
    private ModelAndView editTender(@PathVariable String numberT)
    {
        Tender bufTender = tenderRepository.findByNumberT(numberT);
        TenderForm tenderForm = new TenderForm(bufTender,bufTender.getOrder());
        return new ModelAndView("editTender","tenderForm", tenderForm);
    }

    @PostMapping("/editTender/{numberT}")
    private String editTender(@PathVariable String numberT,
                            @Valid Order order,
                            @Valid Tender tender,
                            BindingResult bindingResult,
                            Model model,
                            @RequestParam(required = false, name = "file") MultipartFile file
    ) throws IOException {
        Tender bufTender = tenderRepository.findByNumberT(numberT);
        model.addAttribute("tender", bufTender);
        model.addAttribute("order",bufTender.getOrder());
        return "editTender";
    }
}
