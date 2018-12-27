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
@AllArgsConstructor
@NoArgsConstructor
public class ObjT {
    private int lot;
    private String nameC;
    private int ots;
    private String nameO;
    private String ed;
    private float cen;
    private float cenS;
    private float cenO;
    private float balC;
    private float balCk;
    private float balO;
    private float balOk;
    private float balOb;
    private int rang;
    
    public ObjT (ObjT t) {
        this.setLot(t.getLot());
        this.setNameC(t.getNameC());
        this.setNameO(t.getNameO());
        this.setOts(t.getOts());
        this.setEd(t.getEd());
        this.setCen(t.getCen());
        this.setCenS(t.getCenS());
        this.setCenO(t.getCenO());
        this.setBalC(t.getBalC());
        this.setBalCk(t.getBalCk());
        this.setBalO(t.getBalO());
        this.setBalOk(t.getBalOk());
        this.setBalOb(t.getBalOb());
        this.setRang(t.getRang());
    }

}//ObjT
