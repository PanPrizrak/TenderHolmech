/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.holmech.tender.application.entity.calculations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author User
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Znach {



    private int numberLota;
    private float priceMax;
    private float priceMin;
    private int defermentOfPaymentMax;
    private int defermentOfPaymentMin;

    public Znach (Znach z) {
        this.numberLota = z.getNumberLota();
        this.priceMax = z.getPriceMax();
        this.priceMin = z.getPriceMin();
        this.defermentOfPaymentMax = (int) z.getDefermentOfPaymentMax();
        this.defermentOfPaymentMin = (int) z.getDefermentOfPaymentMin();
    }

}
