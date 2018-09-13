package com.holmech.tender.application.service;

import com.holmech.tender.application.entity.Applicant;
import com.holmech.tender.application.excelparser.ApplicantParseExcel;
import com.holmech.tender.application.repository.ApplicantReposirory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;

@Service
public class ApplicantService  {
    @Autowired
    ApplicantReposirory applicantReposirory;

    public boolean isApplicant(Applicant applicant) {
        Applicant applicantFromDB = applicantReposirory.findByNameA(applicant.getNameA());
        if (applicantFromDB != null) {
            return true;
        }
        return false;
    }

    public boolean addApplicant(Applicant applicant) {
        if (isApplicant(applicant)) {
            return false;
        }
        applicantReposirory.save(applicant);
        return true;
    }

    public void addApplicants(ArrayList<Applicant> applicants) {
        Applicant applicant;
        for(int i = 0 ; i < applicants.size();i++){
            applicant = applicants.get(i);
            addApplicant(applicant);
        }
    }
}
