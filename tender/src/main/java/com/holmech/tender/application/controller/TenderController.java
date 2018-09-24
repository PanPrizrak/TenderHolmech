package com.holmech.tender.application.controller;

import com.holmech.tender.application.entity.Subject;
import com.holmech.tender.application.entity.Tender;
import com.holmech.tender.application.repository.SubjectRepository;
import com.holmech.tender.application.repository.TenderRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class TenderController {
    private final TenderRepository tenderRepository;
    private final SubjectRepository subjectRepository;

    public TenderController(TenderRepository tenderRepository,
                            SubjectRepository subjectRepository) {
        this.tenderRepository = tenderRepository;
        this.subjectRepository = subjectRepository;
    }

    @GetMapping("/tender/{numberT}")
    public String buf(@PathVariable String numberT, Model model) {//
        Tender tenderFromDB = tenderRepository.findByNumberT(numberT);
        Iterable<Subject> subjects = subjectRepository.findByTender(tenderFromDB);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + subjects.iterator().hasNext());
        model.addAttribute("subjects", subjects);
        return "tender";
    }

    @PostMapping("/tender/{numberT}")
    public String save(@PathVariable String numberT, @Va Subject subjectS, Model model){
        Tender tenderFromDB = tenderRepository.findByNumberT(numberT);
        Iterable<Subject> subjects = subjectRepository.findByTender(tenderFromDB);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + subjects.iterator().hasNext());
        model.addAttribute("subjects", subjects);
        System.out.println("!!!!!!!!!!!!!!!!!!Iter subjects == " + subjectS.toString());
        return "tender";
    }
}
