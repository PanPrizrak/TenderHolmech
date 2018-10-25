package com.holmech.tender.application.controller;

import com.holmech.tender.application.entity.WorkerRole;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("orderedit")
public class OrderController {

    @GetMapping
    public String userEditForm(Model model) {
        model.addAttribute("roles", WorkerRole.values());
        return "orderedit";
    }

    @PostMapping
    public String add(@RequestParam("rolesName") String rolesName) {
        System.out.println("!!!!!!!!!!!!  " + rolesName + "     !!!!!!!!!!!!  ");
        return "orderedit";
    }
}
