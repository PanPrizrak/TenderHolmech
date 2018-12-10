package com.holmech.tender.application.form;

import com.holmech.tender.application.entity.Documents;
import com.holmech.tender.application.entity.Subject;

import java.util.ArrayList;
import java.util.List;

public class SubjectAndDocumentsForm {



    private List<Subject> subjectList;
    private List<Documents> documentsList;

    public SubjectAndDocumentsForm() {
    }

    public SubjectAndDocumentsForm(List<Subject> subjectList, List<Documents> documentsList) {
        this.subjectList = subjectList;
        this.documentsList = documentsList;
    }

    public List<Subject> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }

    public List<Documents> getDocumentsList() {
        return documentsList;
    }

    public void setDocumentsList(List<Documents> documentsList) {
        this.documentsList = documentsList;
    }
}
