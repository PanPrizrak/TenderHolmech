package com.holmech.tender.application.controller;

import com.holmech.tender.application.entity.Worker;
import com.holmech.tender.application.repository.WorkerRepository;
import com.holmech.tender.application.service.TenderService;
import com.holmech.tender.application.service.WorkerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("orderedit")
public class OrderController {

    private WorkerService workerService;
    private TenderService tenderService;

    public OrderController(WorkerService workerService,
                           TenderService tenderService) {
        this.workerService = workerService;
        this.tenderService = tenderService;
    }

    @GetMapping("/{numberT}")
    public String userEditForm(@PathVariable String numberT, Model model) {
        addWorkersAndOrderInModel(numberT, model);
        return "orderedit";
    }

    private void addWorkersAndOrderInModel(@PathVariable String numberT, Model model) {
        model.addAttribute("workers",workerService.findAllMemberOfCommission());
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
        workerService.save(worker);
        addWorkersAndOrderInModel(numberT,model);
        //System.out.println("!!!!!!!!!!!!  " + rolesName + "     !!!!!!!!!!!!  ");
        return "redirect:/orderedit/{numberT}";
    }
}
