package com.holmech.tender.application.form;

import com.holmech.tender.application.entity.Documents;
import com.holmech.tender.application.entity.Subject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
public class SubjectAndDocumentsForm {

    private List<Subject> subjectList;
    private List<Documents> documentsList;

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
