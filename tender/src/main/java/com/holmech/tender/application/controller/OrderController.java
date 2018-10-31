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
    public String userEditForm(@PathVariable String numberT,Model model) {
        addModel(numberT,model);
        return "orderedit";
    }

    private void addModel(@PathVariable String numberT,Model model) {
        model.addAttribute("workers",workerRepository.findAll());
        model.addAttribute("order", tenderService.findByNumberT(numberT).getOrder());
    }

    @PostMapping("/{numberT}")
    public String add(@PathVariable String numberT,
                      @ModelAttribute Worker worker,
                      @RequestParam(required = false) Worker thechairman,
                      @RequestParam(required = false) Worker vicechairman,
                      @RequestParam(required = false) Worker secretary,
                      @RequestParam(required = false) List<Worker> commissionmember,
                      Model model) {
        workerRepository.save(worker);
        //System.out.println("!!!!!!!!!!!!  " + rolesName + "     !!!!!!!!!!!!  ");
        addModel(numberT,model);
        return "orderedit";
    }
}
