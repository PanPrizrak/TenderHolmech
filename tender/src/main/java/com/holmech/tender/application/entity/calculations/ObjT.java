/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.holmech.tender.application.entity.calculations;

import java.util.List;

/**
 *
 * @author User
 */
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
    
    public ObjT(int lot, String nameC, int ots, String nameO, String ed, float cen, float cenS, float cenO, float balC, float balCk, float balO, float balOk, float balOb, int rang) {
        this.lot = lot;
        this.nameC = nameC;
        this.ots = ots;
        this.nameO = nameO;
        this.ed = ed;
        this.cen = cen;
        this.cenS = cenS;
        this.cenO = cenO;
        this.balC = balC;
        this.balCk = balCk;
        this.balO = balO;
        this.balOk = balOk;
        this.balOb = balOb;
        this.rang = rang;
    }

    public float getBalC() {
        return balC;
    }

    public void setBalC(float balC) {
        this.balC = balC;
    }

    public float getBalCk() {
        return balCk;
    }

    public void setBalCk(float balCk) {
        this.balCk = balCk;
    }

    public float getBalO() {
        return balO;
    }

    public void setBalO(float balO) {
        this.balO = balO;
    }

    public float getBalOk() {
        return balOk;
    }

    public void setBalOk(float balOk) {
        this.balOk = balOk;
    }

    public float getBalOb() {
        return balOb;
    }

    public void setBalOb(float balOb) {
        this.balOb = balOb;
    }

    public int getRang() {
        return rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
    }
    public ObjT() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getLot() {
        return lot;
    }

    public void setLot(int lot) {
        this.lot = lot;
    }

    public String getNameC() {
        return nameC;
    }

    public void setNameC(String nameC) {
        this.nameC = nameC;
    }

    public int getOts() {
        return ots;
    }

    public void setOts(int ots) {
        this.ots = ots;
    }

    public String getNameO() {
        return nameO;
    }

    public void setNameO(String nameO) {
        this.nameO = nameO;
    }

    public String getEd() {
        return ed;
    }

    public void setEd(String ed) {
        this.ed = ed;
    }

    public float getCen() {
        return cen;
    }

    public void setCen(float cen) {
        this.cen = cen;
    }

    public float getCenS() {
        return cenS;
    }

    public void setCenS(float cenS) {
        this.cenS = cenS;
    }

    public float getCenO() {
        return cenO;
    }

    public void setCenO(float cenO) {
        this.cenO = cenO;
    }

    @Override
    public String toString() {
        return "ObjT{" + "lot=" + lot + ", nameC=" + nameC + ", ots=" + ots + ", nameO=" + nameO + ", ed=" + ed + ", cen=" + cen + ", cenS=" + cenS + ", cenO=" + cenO + ", balC=" + balC + ", balCk=" + balCk + ", balO=" + balO + ", balOk=" + balOk + ", balOb=" + balOb + ", rang=" + rang + '}';
    }
    

}//ObjT
