package com.holmech.tender.application.form;

import com.holmech.tender.application.entity.SubjectAfterTheReduction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectAfterTheReductionForm {

    List<SubjectAfterTheReduction> subjectAfterTheReductionList;

}
