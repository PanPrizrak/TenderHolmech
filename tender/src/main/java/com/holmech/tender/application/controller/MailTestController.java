package com.holmech.tender.application.controller;

import com.holmech.tender.application.service.MailSender;
import com.holmech.tender.application.utilities.CreateDumpDatabase;
import com.holmech.tender.application.utilities.ImportingDatabase;
import com.holmech.tender.application.utilities.UnzipFile;
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
    private final UnzipFile unzipFile;
    private final ImportingDatabase importingDatabase;

    public MailTestController(MailSender mailSender,
                                CreateDumpDatabase createDumpDatabase,
                              UnzipFile unzipFile,
                              ImportingDatabase importingDatabase) {
        this.mailSender = mailSender;
        this.createDumpDatabase = createDumpDatabase;
        this.unzipFile = unzipFile;
        this.importingDatabase = importingDatabase;
    }

    @GetMapping()
    public String toMail(Model model) {
        model.addAttribute("sendTo", "asirozh@gmail.com");
        return "mailtest";
    }

    @PostMapping()
    public String sendMail(@RequestParam("sendTo") String sendTo, @RequestParam(required = false, defaultValue = "") String action, Model model) throws SQLException, IOException, ClassNotFoundException {
        int choice = Integer.valueOf(action).intValue();
        switch (choice){
            case 1:
                mailSender.send(sendTo,"Test sender email", "Prover otpravlennie");
                break;
            case 2:
                createDumpDatabase.createDump();
                break;
            case 3:{
                unzipFile.setZipFile("E:\\PrograFiles\\TenderHolmech\\external\\19_5_2019_10_26_11_tender_database_dump.zip");
                unzipFile.setDestinationFolder("E:\\PrograFiles\\TenderHolmech\\external\\");
                unzipFile.run();
            }
            case 4:{
                importingDatabase.importing();
            }

        }
        //mailSender.send(sendTo,"Test sender email", "Prover otpravlennie");
        model.addAttribute("sendTo", "asirozh@gmail.com");

        return "mailtest";
    }

}
