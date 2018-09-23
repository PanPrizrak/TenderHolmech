package com.holmech.tender.application.controller;

import com.holmech.tender.application.entity.Subject;
import com.holmech.tender.application.entity.Tender;
import com.holmech.tender.application.repository.SubjectRepository;
import com.holmech.tender.application.repository.TenderRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
        Tender tenderFromDB = tenderRepository.findByNameT(numberT);
        Iterable<Subject> subjects = subjectRepository.findByTender(tenderFromDB);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + subjects.iterator().hasNext());
        model.addAttribute("subjects", subjects);
        return "tender";
    }
}
