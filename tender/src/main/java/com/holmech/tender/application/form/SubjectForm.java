package com.holmech.tender.application.form;

import com.holmech.tender.application.entity.Subject;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class SubjectForm {

    private List<Subject> subjectList;

    public List<Subject> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }
}
