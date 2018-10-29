package com.holmech.tender.application.controller;

import com.holmech.tender.application.entity.Worker;
import com.holmech.tender.application.repository.WorkerRepository;
import com.holmech.tender.application.service.TenderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("orderedit")
public class OrderController {

    private WorkerRepository workerRepository;
    private TenderService tenderService;

    public OrderController(WorkerRepository workerRepository,
                           TenderService tenderService) {
        this.workerRepository = workerRepository;
        this.tenderService = tenderService;
    }

    @GetMapping("/{numberT}")
    public String userEditForm(@PathVariable String numberT, Model model) {
        model.addAttribute("workers",workerRepository.findAll());
        model.addAttribute("order", tenderService.findByNumberT(numberT).getOrder());
        return "orderedit";
    }

    @PostMapping("/{numberT}")
    public String add(@PathVariable String numberT,
                      @ModelAttribute Worker worker,
                      @RequestParam String thechairman,
                      @RequestParam String vicechairman,
                      @RequestParam String secretary,
                      @RequestParam List<String> commissionmember) {
        workerRepository.save(worker);
        //System.out.println("!!!!!!!!!!!!  " + rolesName + "     !!!!!!!!!!!!  ");
        return "orderedit";
    }
}
