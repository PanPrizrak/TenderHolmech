package com.holmech.tender.application.form;

import com.holmech.tender.application.entity.Documents;
import com.holmech.tender.application.entity.Subject;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class DocumentsForm {

    private List<Documents> documentsList;


    public List<Documents> getDocumentsList() {
        return documentsList;
    }

    public void setDocumentsList(List<Documents> documentsList) {
        this.documentsList = documentsList;
    }
}
