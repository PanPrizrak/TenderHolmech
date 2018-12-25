package com.holmech.tender.application.controller;

import com.holmech.tender.application.entity.Order;
import com.holmech.tender.application.entity.Worker;
import com.holmech.tender.application.form.MemberCommissionForm;
import com.holmech.tender.application.service.TenderService;
import com.holmech.tender.application.service.WorkerRService;
import com.holmech.tender.application.service.WorkerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("addworker")
public class WorkerController {

    private final WorkerService workerService;
    private final TenderService tenderService;
    private Order bufOrderForWorkerRole;

    public WorkerController(WorkerService workerService,
                            TenderService tenderService) {
        this.workerService = workerService;
        this.tenderService = tenderService;
    }

    @GetMapping("/{numberT}")
    public String userEditForm(@PathVariable String numberT, Model model) {
        addWorkersAndOrderInModel(numberT, model);
        return "addworker";
    }

    private void addWorkersAndOrderInModel(@PathVariable String numberT, Model model) {
        model.addAttribute("workers", workerService.findAllMemberOfCommission());
        bufOrderForWorkerRole = tenderService.findByNumberT(numberT).getOrder();
        model.addAttribute("order", bufOrderForWorkerRole);
        model.addAttribute("numberT", numberT);

    }

    @PostMapping("/{numberT}")
    public String add(@PathVariable String numberT,
                      @ModelAttribute Worker newWorker,
                      Model model) {
            workerService.save(newWorker);
        addWorkersAndOrderInModel(numberT, model);
        return "redirect:/addworker/{numberT}";
    }
}
