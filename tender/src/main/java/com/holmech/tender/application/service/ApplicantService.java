package com.holmech.tender.application.service;

import com.holmech.tender.application.entity.Applicant;
import com.holmech.tender.application.repository.ApplicantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ApplicantService  {
    @Autowired
    ApplicantRepository applicantReposirory;

    public void addApplicants(ArrayList<Applicant> applicants) {
        for(int i = 0 ; i < applicants.size();i++){
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

    public Applicant findByNameA(String bufApplicantNameA){
        bufApplicantNameA = bufApplicantNameA.replace('"', '\"');
        return applicantReposirory.findByNameA(bufApplicantNameA);
    }

    public void updateApplicant(Applicant applicant){
        applicantReposirory.save(applicant);
    }
}
