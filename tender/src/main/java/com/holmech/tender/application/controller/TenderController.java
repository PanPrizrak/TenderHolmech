package com.holmech.tender.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.misc.Contended;

@Controller
@RequestMapping("/tender/{tender.numderT}")
public class TenderController {

    @GetMapping
    public String buf(){
        return "tender";
    }
}
