package com.holmech.tender.application.service;

import com.holmech.tender.application.entity.Applicant;
import com.holmech.tender.application.repository.ApplicantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ApplicantService {
    @Autowired
    ApplicantRepository applicantReposirory;

    public void addApplicants(ArrayList<Applicant> applicants) {
        for (int i = 0; i < applicants.size(); i++) {
            addApplicant(applicants.get(i));
        }
    }

    public boolean addApplicant(Applicant applicant) {
        if (isApplicant(applicant)) {
            return false;
        }
        applicantReposirory.save(applicant);
        return true;
    }

    public boolean isApplicant(Applicant applicant) {
        Applicant applicantFromDB = applicantReposirory.findByNameA(applicant.getNameA());
        if (applicantFromDB != null) {
            return true;
        }
        return false;
    }

    public Applicant findByNameA(String bufApplicantNameA) {
        bufApplicantNameA = bufApplicantNameA.replace('"', '\"');
        return applicantReposirory.findByNameA(bufApplicantNameA);
    }

    public String getFirstWordName(String nameApplicant) {
        if(nameApplicant.equalsIgnoreCase("\"")) {
            int firstIndexChar = nameApplicant.indexOf("\"");
            int lastIndexChar = nameApplicant.indexOf(" ", firstIndexChar);
            int lastIndexCharTwo = nameApplicant.lastIndexOf("\"");
            if (lastIndexChar < 0)
                lastIndexChar = lastIndexCharTwo;
            return nameApplicant.substring(firstIndexChar, lastIndexChar);
        } else {
            int lastIndexChar = nameApplicant.indexOf(" ", 2);
            return nameApplicant.substring(2, lastIndexChar);
        }
    }

    public Applicant findByIdA(Long idA) {
        return applicantReposirory.findById(idA).get();
    }

    public void updateApplicant(Applicant applicant) {
        applicantReposirory.save(applicant);
    }
}
