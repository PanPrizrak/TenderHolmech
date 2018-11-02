package com.holmech.tender.application.controller;

import com.holmech.tender.application.entity.Order;
import com.holmech.tender.application.entity.Worker;
import com.holmech.tender.application.form.MemberCommissionForm;
import com.holmech.tender.application.repository.WorkerRReposytory;
import com.holmech.tender.application.service.TenderService;
import com.holmech.tender.application.service.WorkerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("orderedit")
public class OrderController {

    private final WorkerService workerService;
    private final TenderService tenderService;
    private final WorkerRReposytory workerRReposytory;
    private Order bufOrderForWorkerRole;

    public OrderController(WorkerService workerService,
                           TenderService tenderService,
                           WorkerRReposytory workerRReposytory) {
        this.workerService = workerService;
        this.tenderService = tenderService;
        this.workerRReposytory = workerRReposytory;
    }

    @GetMapping("/{numberT}")
    public String userEditForm(@PathVariable String numberT, Model model) {
        addWorkersAndOrderInModel(numberT, model);
        return "orderedit";
    }

    private void addWorkersAndOrderInModel(@PathVariable String numberT, Model model) {
        model.addAttribute("workers", workerService.findAllMemberOfCommission());
        bufOrderForWorkerRole = tenderService.findByNumberT(numberT).getOrder();
        model.addAttribute("order", bufOrderForWorkerRole);
    }

    @PostMapping("/{numberT}")
    public String add(@PathVariable String numberT,
                      @ModelAttribute Worker newWorker,
                      @ModelAttribute MemberCommissionForm memberCommissionForm,
                      Model model) {
        if (newWorker != null) {
            workerService.save(newWorker);
        }


        addWorkersAndOrderInModel(numberT, model);
        return "redirect:/orderedit/{numberT}";
    }
}
