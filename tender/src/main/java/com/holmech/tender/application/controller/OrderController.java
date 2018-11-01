package com.holmech.tender.application.controller;

import com.holmech.tender.application.entity.Order;
import com.holmech.tender.application.entity.Worker;
import com.holmech.tender.application.form.MemberCommissionForm;
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
                      @ModelAttribute Order order,
                      /*@ModelAttribute Worker thechairman,
                      @ModelAttribute Worker vicechairman,
                      @ModelAttribute Worker secretary,
                      @ModelAttribute List<Worker> commissionmember,*/
                      @ModelAttribute MemberCommissionForm memberCommissionForm,
                      Model model) {
        workerService.save(worker);

        //System.out.println("!!!!!!!!!!!!  " + rolesName + "     !!!!!!!!!!!!  ");
        addWorkersAndOrderInModel(numberT,model);
        return "redirect:/orderedit/{numberT}";
    }
}
