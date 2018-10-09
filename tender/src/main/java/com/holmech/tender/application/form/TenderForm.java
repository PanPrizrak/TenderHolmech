package com.holmech.tender.application.form;

import com.holmech.tender.application.entity.Order;
import com.holmech.tender.application.entity.Tender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TenderForm {

    private List<Tender> tenderList;
}
