package com.holmech.tender.application.controller;

import com.holmech.tender.application.entity.Applicant;
import com.holmech.tender.application.entity.Order;
import com.holmech.tender.application.entity.Tender;
import com.holmech.tender.application.excelparser.ApplicantParseExcel;
import com.holmech.tender.application.excelparser.ExcelParser;
import com.holmech.tender.application.repository.ApplicantReposirory;
import com.holmech.tender.application.repository.OrderRepository;
import com.holmech.tender.application.repository.TenderRepository;
import com.holmech.tender.application.service.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/journal")
public class JournalController {

    private final ApplicantService applicantService;

    @Autowired
    private TenderRepository tenderRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ApplicantReposirory applicantReposirory;

    @Value("${upload.path}")
    private String uploadPath;

    public JournalController(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @GetMapping()
    public String journal(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<Tender> tenders;

        if (filter != null && !filter.isEmpty()) {
            tenders = tenderRepository.findByNameT(filter);
        } else {
            tenders = tenderRepository.findAll();
        }

        model.addAttribute("tenders", tenders);
        model.addAttribute("filter", filter);
        return "journal";
    }

    @PostMapping()
    public String add(
            @RequestParam(required = false, name="idtender") Long idtender,
            @Valid Order order,
            @Valid Tender tender,
            BindingResult bindingResult,
            Model model,
            @RequestParam(required = false, name = "file") MultipartFile file
    ) throws IOException {

        if(idtender!=null){
            ExcelParser bufExcel = new ExcelParser();
            Optional<Tender> bufTender = tenderRepository.findById(idtender);
            String bufPath = new String(new File("").getAbsoluteFile()+"/tender/src/main/resources/uploads/"+bufTender.get().getFilename());
            applicantService.addApplicants(ApplicantParseExcel.parse(new File(bufPath)));
            model.addAttribute("tender",null);
        }
        else {
            orderRepository.save(order);
            tender.setOrder(order);
            if (bindingResult.hasErrors()) {
                Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
                model.mergeAttributes(errorsMap);
                model.addAttribute("tender", tender);
                model.addAttribute("oder", order);
            } else {
                saveFile(tender, file);
                model.addAttribute("tender", null);
                System.out.println("!!!!!!!!!!!!!!!!!!!" + tender.getDateT().toString());
                tenderRepository.save(tender);
            }
        }
            Iterable<Tender> tenders = tenderRepository.findAll();
            model.addAttribute("tenders", tenders);
        System.out.println(model.containsAttribute("tender"));
        return "journal";
    }

    private void saveFile(@Valid Tender tender, @RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.isDirectory()) {
                uploadDir.mkdirs();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            tender.setFilename(resultFilename);
        }
    }

    /*@PostMapping()
    public String deleteJournal(@RequestParam("idtender") Long idtender, Model model) {

        /*tenderRepository.deleteById(idtender);
        Iterable<Tender> tenders = tenderRepository.findAll();

        model.addAttribute("tenders", tenders);*

    }*/
}
