/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.holmech.tender.application.entity.calculations;

/**
 *
 * @author User
 */
public class Znach {
    private int nomL;
    private float cenaMax;
    private float cenaMin;
    private int otsMax;
    private int otsMin;

    public Znach() {
    }
    public Znach (Znach z) {
        this.nomL = z.getNomL();
        this.cenaMax = z.getCenaMax();
        this.cenaMin = z.getCenaMin();
        this.otsMax = (int) z.getOtsMax();
        this.otsMin = (int) z.getOtsMin();
    }

    public Znach(int nomL, float cenMax, float cenaMin, int otsMax, int otsMin) {
        this.nomL = nomL;
        this.cenaMax = cenMax;
        this.cenaMin = cenaMin;
        this.otsMax = (int) otsMax;
        this.otsMin = (int) otsMin;
    }

    public int getNomL() {
        return nomL;
    }

    public void setNomL(int nomL) {
        this.nomL = nomL;
    }

    public float getCenaMax() {
        return cenaMax;
    }

    public void setCenaMax(float cenaMax) {
        this.cenaMax = cenaMax;
    }

    public float getCenaMin() {
        return cenaMin;
    }

    public void setCenaMin(float cenaMin) {
        this.cenaMin = cenaMin;
    }

    public int getOtsMax() {
        return otsMax;
    }

    public void setOtsMax(int otsMax) {
        this.otsMax = otsMax;
    }

    public int getOtsMin() {
        return otsMin;
    }

    public void setOtsMin(int otsMin) {
        this.otsMin = otsMin;
    }

    @Override
    public String toString() {
        return "Znach{" + "nomL=" + nomL + ", cenMax=" + cenaMax + ", cenaMin=" + cenaMin + ", otsMax=" + otsMax + ", otsMin=" + otsMin + '}';
    }

}
