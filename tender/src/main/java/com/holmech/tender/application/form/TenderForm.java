package com.holmech.tender.application.form;

import com.holmech.tender.application.entity.Order;
import com.holmech.tender.application.entity.Tender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TenderForm {

    private Tender tender;
    private Order Order;

}
