package com.holmech.tender.application.controller;

import com.holmech.tender.application.service.MailSender;
import com.holmech.tender.application.utilities.CreateDumpDatabase;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.sql.SQLException;


@Controller
@RequestMapping("mailtest")
public class MailTestController {

    private final MailSender mailSender;
    private final CreateDumpDatabase createDumpDatabase;

    public MailTestController(MailSender mailSender,
                                CreateDumpDatabase createDumpDatabase) {
        this.mailSender = mailSender;
        this.createDumpDatabase = createDumpDatabase;
    }

    @GetMapping()
    public String toMail(Model model) {
        model.addAttribute("sendTo", "asirozh@gmail.com");
        return "mailtest";
    }

    @PostMapping()
    public String sendMail(@RequestParam("sendTo") String sendTo,Model model) throws SQLException, IOException, ClassNotFoundException {
        //mailSender.send(sendTo,"Test sender email", "Prover otpravlennie");
        model.addAttribute("sendTo", "asirozh@gmail.com");
            createDumpDatabase.createDump();
        return "mailtest";
    }

}
