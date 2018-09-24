package com.holmech.tender.application.controller;

import com.holmech.tender.application.entity.Subject;
import com.holmech.tender.application.entity.Tender;
import com.holmech.tender.application.repository.SubjectRepository;
import com.holmech.tender.application.repository.TenderRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import javax.validation.Valid;
import java.util.List;

@Controller
public class TenderController {

    @InitBinder
    public void initBinder(Subject dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        if (target.getClass() == Subject.class) {

            // Register to handle the conversion between the multipart object
            // and byte array.
            dataBinder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        }
    }

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
    public String save(@PathVariable String numberT, @ModelAttribute("mySubject") Subject subjectS, Model model){
        Tender tenderFromDB = tenderRepository.findByNumberT(numberT);
        Iterable<Subject> subjects = subjectRepository.findByTender(tenderFromDB);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + subjects.iterator().hasNext());
        model.addAttribute("subjects", subjects);
        System.out.println("!!!!!!!!!!!!!!!!!!Iter subjects == " + initBinder(subjectS));
        return "tender";
    }
}
