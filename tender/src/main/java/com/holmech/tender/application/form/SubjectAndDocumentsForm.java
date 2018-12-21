package com.holmech.tender.application.form;

import com.holmech.tender.application.entity.Documents;
import com.holmech.tender.application.entity.Subject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectAndDocumentsForm {

    private List<Subject> subjectList;
    private List<Documents> documentsList;

}
