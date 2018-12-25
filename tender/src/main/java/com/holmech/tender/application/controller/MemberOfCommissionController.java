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
@RequestMapping("memberofcommission")
public class MemberOfCommissionController {

    private final WorkerService workerService;
    private final TenderService tenderService;
    private final WorkerRService workerRService;
    private Order bufOrderForWorkerRole;

    public MemberOfCommissionController(WorkerService workerService,
                                        TenderService tenderService,
                                        WorkerRService workerRService) {
        this.workerService = workerService;
        this.tenderService = tenderService;
        this.workerRService = workerRService;
    }

    @GetMapping("/{numberT}")
    public String userEditForm(@PathVariable String numberT, Model model) {
        addWorkersAndOrderInModel(numberT, model);
        return "memberofcommission";
    }

    private void addWorkersAndOrderInModel(@PathVariable String numberT, Model model) {
        model.addAttribute("workers", workerService.findAllMemberOfCommission());
        bufOrderForWorkerRole = tenderService.findByNumberT(numberT).getOrder();
        model.addAttribute("order", bufOrderForWorkerRole);
        model.addAttribute("numberT",numberT);
    }

    @PostMapping("/{numberT}")
    public String add(@PathVariable String numberT,
                      @ModelAttribute Worker newWorker,
                      @ModelAttribute MemberCommissionForm memberCommissionForm,
                      Model model) {
        workerRService.saveMembersCommission(bufOrderForWorkerRole,memberCommissionForm);
        addWorkersAndOrderInModel(numberT, model);
        return "redirect:/memberofcommission/{numberT}";
    }
}
