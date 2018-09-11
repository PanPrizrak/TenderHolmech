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
public class Bal {
    
    private int pos;
    private int lot;
    private float balO;
    private int rang;

    public Bal() {
    }

    public Bal(int pos, int lot, float balO, int rang) {
        this.pos = pos;
        this.lot = lot;
        this.balO = balO;
        this.rang = rang;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getLot() {
        return lot;
    }

    public void setLot(int lot) {
        this.lot = lot;
    }

    public float getBalO() {
        return balO;
    }

    public void setBalO(float balO) {
        this.balO = balO;
    }

    public int getRang() {
        return rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
    }

    @Override
    public String toString() {
        return "Bal{" + "pos=" + pos + ", lot=" + lot + ", balO=" + balO + ", rang=" + rang + '}';
    }
    
}
