package com.holmech.tender.application.controller;

import com.holmech.tender.application.entity.Applicant;
import com.holmech.tender.application.parser.fromexcel.ApplicantParseExcel;
import com.holmech.tender.application.service.MailSender;
import com.holmech.tender.application.utilities.CreateDumpDatabase;
import com.holmech.tender.application.utilities.ImportingDatabase;
import com.holmech.tender.application.utilities.PathFromOS;
import com.holmech.tender.application.utilities.UnzipFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


@Controller
@RequestMapping("mailtest")
public class MailTestController {

    private final MailSender mailSender;
    private final CreateDumpDatabase createDumpDatabase;
    private final UnzipFile unzipFile;
    private final ImportingDatabase importingDatabase;

    @Value("${upload.path}")
    private String uploadPath;

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
    public String sendMail(@RequestParam("sendTo") String sendTo,
                           @RequestParam("file") MultipartFile file,
                           @RequestParam(required = false, defaultValue = "") String action,
                           Model model) throws SQLException, IOException, ClassNotFoundException {
        int choice = Integer.valueOf(action).intValue();
        switch (choice){
            case 1:
                mailSender.send(sendTo,"Test sender email", "Prover otpravlennie");
                break;
            case 2:
                createDumpDatabase.createDump();
                break;
            case 3:{

                if (file != null && !file.getOriginalFilename().isEmpty()) {//getOriginalFilename work  only in chrome
                    File uploadDir = new File(uploadPath + "Unzip" + PathFromOS.getPath());
                    if (!uploadDir.isDirectory()) {
                        uploadDir.mkdirs();
                    }
                    // String uuidFile = UUID.randomUUID().toString();
                    String bufNameFile = new String(uploadPath + "Dump" + PathFromOS.getPath() + file.getName());
                    file.transferTo(new File(bufNameFile));

                    unzipFile.setZipFile(bufNameFile);
                    unzipFile.setDestinationFolder(uploadPath + "Unzip" + PathFromOS.getPath());
                    unzipFile.run();
                }

            }
            case 4:{
                if (file != null && !file.getOriginalFilename().isEmpty()) {//getOriginalFilename work  only in chrome
                    File uploadDir = new File(uploadPath + "Unzip" + PathFromOS.getPath());
                    if (!uploadDir.isDirectory()) {
                        uploadDir.mkdirs();
                    }
                    // String uuidFile = UUID.randomUUID().toString();
                    String bufNameFile = new String(uploadPath + "Dump" + PathFromOS.getPath() + file.getName());
                    file.transferTo(new File(bufNameFile));

                    unzipFile.setZipFile(bufNameFile);
                    unzipFile.setDestinationFolder(uploadPath + "Unzip" + PathFromOS.getPath());
                    unzipFile.run();
                    importingDatabase.importing();
                }

            }

        }

        //mailSender.send(sendTo,"Test sender email", "Prover otpravlennie");
        model.addAttribute("sendTo", "asirozh@gmail.com");

        return "mailtest";
    }

}
