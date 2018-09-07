package com.holmech.tender.application.controller;

import com.holmech.tender.application.entity.Order;
import com.holmech.tender.application.entity.Tender;
import com.holmech.tender.application.repository.OrderRepository;
import com.holmech.tender.application.repository.TenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
public class JournalController {

    @Autowired
    private TenderRepository tenderRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/journal")
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

    @PostMapping("/journal")
    public String add(
            @Valid Order order,
            @Valid Tender tender,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

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

            System.out.println("!!!!!!!!!!!!!!!!!!!"+tender.getDateT().toString());
            tenderRepository.save(tender);
        }

        Iterable<Tender> tenders = tenderRepository.findAll();

        model.addAttribute("tenders", tenders);

        return "journal";
    }

    private void saveFile(@Valid Tender tender, @RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            tender.setFilename(resultFilename);
        }
    }

    @PostMapping("/journal/delete")
    public String deleteJournal(@RequestParam("idtender") Long idtender, Model model) {

        tenderRepository.deleteById(idtender);
        Iterable<Tender> tenders = tenderRepository.findAll();

        model.addAttribute("tenders", tenders);

        return "journal";
    }
}
