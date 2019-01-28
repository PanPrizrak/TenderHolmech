package com.holmech.tender.application.controller;

import com.holmech.tender.application.entity.Order;
import com.holmech.tender.application.entity.Worker;
import com.holmech.tender.application.form.MemberCommissionForm;
import com.holmech.tender.application.repository.OrderRepository;
import com.holmech.tender.application.service.TenderService;
import com.holmech.tender.application.service.WorkerRService;
import com.holmech.tender.application.service.WorkerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("orderedit")
public class OrderController {

    private final OrderRepository orderRepository;
    private final TenderService tenderService;

    public OrderController(OrderRepository orderRepository, TenderService tenderService) {
        this.orderRepository = orderRepository;
        this.tenderService = tenderService;
    }

    @GetMapping("/{numberT}")
    public String userEditForm(@PathVariable String numberT, Model model) {
        addOrderInModel(numberT, model);
        return "orderedit";
    }

    private void addOrderInModel(@PathVariable String numberT, Model model) {
        model.addAttribute("order", tenderService.findByNumberT(numberT).getOrder());//todo номер тендера
        model.addAttribute("numberT", numberT);
    }

    @PostMapping("/{numberT}")
    public String add(@PathVariable String numberT,
                      @ModelAttribute("editedOrder") Order editedOrder,
                      Model model) {
        orderRepository.save(editedOrder);
        addOrderInModel(numberT, model);
        return "redirect:/orderedit/{numberT}";
    }
}
