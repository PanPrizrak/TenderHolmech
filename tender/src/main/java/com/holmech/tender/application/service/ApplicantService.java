package com.holmech.tender.application.service;

import com.holmech.tender.application.entity.Applicant;
import com.holmech.tender.application.repository.ApplicantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ApplicantService {
    @Autowired
    ApplicantRepository applicantReposirory;
    final Logger logger = LoggerFactory.getLogger(ApplicantService.class);

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
        String bufResult = new String();
        if(nameApplicant.contains( "\"")) {
            int firstIndexChar = nameApplicant.indexOf("\"");
            int lastIndexChar = nameApplicant.indexOf(" ", firstIndexChar);
            int lastIndexCharTwo = nameApplicant.indexOf("\"", firstIndexChar+1);
            if (lastIndexChar < 0 || lastIndexChar > lastIndexCharTwo)
                lastIndexChar = lastIndexCharTwo;
            bufResult = nameApplicant.substring(firstIndexChar+1, lastIndexChar);
            logger.info("Ishodnaya stroka --{}   Result stroka --{}",nameApplicant,bufResult);
            return bufResult;
        } else {
            int lastIndexChar = nameApplicant.indexOf(" ", 3);
            bufResult = nameApplicant.substring(2, lastIndexChar);
            logger.info("Ishodnaya stroka --{}   Result stroka --{}",nameApplicant,bufResult);
            return bufResult;
        }
    }

    public Applicant findByIdA(Long idA) {
        return applicantReposirory.findById(idA).get();
    }

    public void updateApplicant(Applicant applicant) {
        applicantReposirory.save(applicant);
    }
}
