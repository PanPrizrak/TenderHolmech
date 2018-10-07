package com.holmech.tender.application.controller;

import com.holmech.tender.application.entity.Applicant;
import com.holmech.tender.application.entity.Order;
import com.holmech.tender.application.entity.Tender;
import com.holmech.tender.application.excelparser.ApplicantParseExcel;
import com.holmech.tender.application.form.SubjectAndDocumentsForm;
import com.holmech.tender.application.form.TenderForm;
import com.holmech.tender.application.repository.OrderRepository;
import com.holmech.tender.application.repository.TenderRepository;
import com.holmech.tender.application.service.ApplicantService;
import com.holmech.tender.application.service.DocumentsService;
import com.holmech.tender.application.service.SubjectService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


@Controller
@RequestMapping("/journal")
public class JournalController {

    //initial in lombook anatation @AllArgsConstructor
    private final ApplicantService applicantService;
    private final TenderRepository tenderRepository;
    private final OrderRepository orderRepository;
    private final DocumentsService documentsService;
    private final SubjectService subjectService;

    public JournalController(ApplicantService applicantService,
                             TenderRepository tenderRepository,
                             OrderRepository orderRepository,
                             DocumentsService documentsService,
                             SubjectService subjectService) {
        this.applicantService = applicantService;
        this.tenderRepository = tenderRepository;
        this.orderRepository = orderRepository;
        this.documentsService = documentsService;
        this.subjectService = subjectService;
    }

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping()
    public String journal(Model model) {
        Iterable<Tender> tenders;
        tenders = tenderRepository.findAll();
        model.addAttribute("tenders", tenders);
        return "journal";
    }

    @PostMapping()
    public String add(
            @RequestParam(required = false, name = "idtender") Long idtender,
            @ModelAttribute("tenderForm") TenderForm tenderform,
            @Valid Order order,
            @Valid Tender tender,
            BindingResult bindingResult,
            Model model,
            @RequestParam(required = false, name = "file") MultipartFile file
    ) throws IOException {

        if (idtender != null) {
            Optional<Tender> bufTender = tenderRepository.findById(idtender);
            String bufPath = new String(uploadPath + "\\" + bufTender.get().getFilename());
            if (!documentsService.isDocuments(bufTender.get())) {
                ArrayList<Applicant> applicantArrayList = ApplicantParseExcel.parse(new File(bufPath));
                applicantService.addApplicants(applicantArrayList);//save applicants
                if (documentsService.addDocumentsFromExcel(bufTender, applicantArrayList)) {//save in documents
                    subjectService.addSubjectFromExcel(bufTender, applicantArrayList);
                }
            }
            model.addAttribute("tender", null);
        } else {
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
        TenderForm tenderForm = new TenderForm();
        return "joirnal";
    }


    private void saveFile(@Valid Tender tender, @RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {//getOriginalFilename work  only in chrome
            System.out.println(file.getName().toString() + " getOrigi" + file.getOriginalFilename());
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
}//JournalController
