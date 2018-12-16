package com.holmech.tender.application.controller;

import com.holmech.tender.application.entity.Applicant;
import com.holmech.tender.application.entity.Contacts;
import com.holmech.tender.application.repository.ContactsRepository;
import com.holmech.tender.application.service.ApplicantService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("addcontactfor")
public class AddContactController {

    ContactsRepository contactsRepository;
    ApplicantService applicantService;

    public AddContactController(ContactsRepository contactsRepository,
                                ApplicantService applicantService) {
        this.contactsRepository = contactsRepository;
        this.applicantService = applicantService;
    }

    @GetMapping("/{forSomeone}/{idForSomeone}")
    public String userEditForm() {
        return "addcontactfor";
    }

    @PostMapping("/{forSomeone}/{idForSomeone}")
    public String add(@PathVariable String forSomeone,
                      @PathVariable Long idForSomeone,
                      @RequestParam(required = false) String emailFromHTML,
                      @RequestParam(required = false) String phoneFromHTML) {
        Contacts contacts = new Contacts();
        contacts.setEmail(emailFromHTML);
        contacts.setPhone(phoneFromHTML);
        contactsRepository.save(contacts);
        switch (forSomeone) {
            case "applicant": {
                Contacts contactsFromDB = contactsRepository.findByPhone(phoneFromHTML);
                Applicant applicant = new Applicant();
                applicant = applicantService.findByIdA(idForSomeone);
                List<Contacts> contactsList = new ArrayList<>();
                if(applicant.getContactsList().isEmpty()) {
                  contactsList.add(contactsFromDB);
                } else {
                    contactsList = applicant.getContactsList();
                    contactsList.add(contactsFromDB);
                }
                applicant.setContactsList(contactsList);
                applicantService.updateApplicant(applicant);
                break;
            }
        }
        return "redirect:/addcontactfor/{forSomeone}/{idForSomeone}";
    }

}
