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
        addWorkersAndOrderInModel(numberT, model);
        return "orderedit";
    }

    private void addWorkersAndOrderInModel(@PathVariable String numberT, Model model) {
        model.addAttribute("workers",workerRepository.findAll());
        model.addAttribute("order", tenderService.findByNumberT(numberT).getOrder());
    }

    @PostMapping("/{numberT}")
    public String add(@PathVariable String numberT,
                      @ModelAttribute Worker worker,
                      @RequestParam(required = false) String thechairman,
                      @RequestParam(required = false) String vicechairman,
                      @RequestParam(required = false) String secretary,
                      @RequestParam(required = false) List<String> commissionmember,
                      Model model) {
        workerRepository.save(worker);
        addWorkersAndOrderInModel(numberT,model);
        //System.out.println("!!!!!!!!!!!!  " + rolesName + "     !!!!!!!!!!!!  ");
        return "redirect:/orderedit";
    }
}
