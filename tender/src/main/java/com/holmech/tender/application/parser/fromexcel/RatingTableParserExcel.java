/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.holmech.tender.application.parser.fromexcel;


import com.holmech.tender.application.entity.Tender;
import com.holmech.tender.application.entity.calculations.Bal;
import com.holmech.tender.application.entity.calculations.ObjT;
import com.holmech.tender.application.entity.calculations.Znach;
import com.holmech.tender.application.repository.ApplicantRepository;
import lombok.Data;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;

@Data
@Service
public class RatingTableParserExcel {

    @Value("${upload.path}")
    private String uploadPath;

    static XSSFRow row;
    private  float cenaK = (float) 0.0;//(float) 0.8;
    private  float otsK = (float) 0.0;//0.2;

    @Autowired
    private ApplicantRepository applicantRepository;
    private File fileJournal;

    private ArrayList<ObjT> objTs;
    private ArrayList<ObjT> parSravs;
    private ArrayList<Znach> znachs;


    private void getRaschet(ObjT t, float maxPrice, float minPrice, int maxPayment, int minPayment) {

        if (maxPrice != minPrice) {
            t.setBalC((float) (1 + (maxPrice - t.getCenO()) / (maxPrice - minPrice) * 9));
        } else {
            t.setBalC((float) 1.0);
        }
        t.setBalCk((float) t.getBalC() * cenaK);

        if (maxPayment != minPayment) {
            float b1;
            float b2;
            float b3;
            b1 = (float) t.getOts() - minPayment;
            b2 = (float) maxPayment - minPayment;
            b3 = (float) b1 / b2 * 9;
            t.setBalO((float) (1 + b3));
        } else {
            t.setBalO((float) 1.0);
        }
        t.setBalOk((float) t.getBalO() * otsK);

        t.setBalOb((float) t.getBalOk() + t.getBalCk());
        
        /*float balCk = (float) t.getBalC() * cenaK;
        float balOk = (float) t.getBalO() * otsK;
        if (balCk < 0 || balCk > 10 || balOk < 0 || balOk > 10 )
            JOptionPane.showMessageDialog(null, t.toString()+"!!!"+maxC+"!!!"+minC+"!!!"+maxO+"!!!"+minO);*/
    }

    public void parse(ArrayList<ObjT> objTList, Tender tender) {


        int bufPriceFactor = Integer.parseInt(tender.getPriceFactor());
        int bufPaymentFactor = Integer.parseInt(tender.getPaymentFactor());
        float bufCenaK = (float) bufPriceFactor/100;
        float bufOtsK = (float) bufPaymentFactor/100;
        setCenaK(bufCenaK);
        setOtsK(bufOtsK);

        setFileJournal(new File(uploadPath+tender.getFilename()));

        znachs = definitionOfExternsOfValues(objTList);

        //Расчет балов
        for (int i = 0; i < objTList.size(); i++) {

            int maxO = znachs.get(objTList.get(i).getLot() - 1).getDefermentOfPaymentMax();
            int minO = znachs.get(objTList.get(i).getLot() - 1).getDefermentOfPaymentMin();
            float maxC = znachs.get(objTList.get(i).getLot() - 1).getPriceMax();
            float minC = znachs.get(objTList.get(i).getLot() - 1).getPriceMin();
            if (objTList.get(i).getCenO() != 0) {
                getRaschet(objTList.get(i), maxC, minC, maxO, minO);
            }
        }


        //попарное сравнение
        setParSravs(pairwiseСomparison(objTList));

        //Заполнение объеекта Bal
        ArrayList<Bal> bals = new ArrayList<Bal>();
        for (int i = 0; i < objTList.size(); i++) {
            if (objTList.get(i).getCenO() != 0) {
                Bal bal = new Bal();
                bal.setPos(i);
                bal.setLot(objTList.get(i).getLot());
                bal.setBalO(objTList.get(i).getBalOb());
                bals.add(bal);
            }
        }

        //сортировка  в каждом лоте по убыванию общих балов
        sortBals(bals);

        //присвоение рангов
        for (int i = 0; i < bals.size(); i++) {
            objTList.get(bals.get(i).getPos()).setRang((int) bals.get(i).getRang());
        }
        setObjTs(objTList);

        writeResultInExcel();

    }//!!!!!!!!!!!!!!!End parse

    @NotNull
    private ArrayList<Znach> definitionOfExternsOfValues(ArrayList<ObjT> objT) {
        //Определини экстернов значений
        ArrayList<Znach> znachs = new ArrayList<Znach>();
        Znach znach;
        int pos = 0;
        for (int i = 0; i < objT.get(objT.size() - 1).getLot(); i++) {

            //int lot = pos-1;
            float minC = objT.get(pos).getCenO();
            float maxC = objT.get(pos).getCenO();
            int minO = objT.get(pos).getOts();
            int maxO = objT.get(pos).getOts();
            System.out.println("Lot #" + (i+1));
            while (pos <= (objT.size() - 1) && objT.get(pos).getLot() == (i + 1)  ) {
                System.out.println("Iteraciy!!!!!!!!!");
                if (objT.get(pos).getCenO() > maxC && objT.get(pos).getCenO() != 0) {
                    maxC = objT.get(pos).getCenO();
                }
                if (objT.get(pos).getCenO() < minC && objT.get(pos).getCenO() != 0) {
                    minC = objT.get(pos).getCenO();
                }

                if (objT.get(pos).getOts() > maxO) {
                    maxO = objT.get(pos).getOts();
                }
                if (objT.get(pos).getOts() < minO) {
                    minO = objT.get(pos).getOts();
                }
                if (pos <= objT.size() - 1) {
                    pos++;
                }//if
                //pos++;
            }

            znach = new Znach((int) (i + 1), maxC, minC, maxO, minO,"0");
            znachs.add(znach);

        }
        return znachs;
    }

    private  ArrayList<ObjT> pairwiseСomparison(ArrayList<ObjT> objT) {
        ArrayList<ObjT> parSrav = new ArrayList<ObjT>();
        //System.out.println(objT.size());
        for (int i = 0; i < objT.size() - 1; i++) {
            ObjT bufO;// = new ObjT(objT.get(i));
            //int k = i + 1;
            float maxC;
            float minC;
            int maxO;
            int minO;
            if (i < objT.size() - 2 && objT.get(i).getCenO() != 0) {
                for (int k = i + 1; k < objT.size(); k++) {

                    if (objT.get(k).getCenO() != 0 && objT.get(i).getLot() == objT.get(k).getLot()) {
                        bufO = new ObjT(objT.get(i));
                        ObjT bufOp = new ObjT(objT.get(k));

                        if (objT.get(i).getCenO() > objT.get(k).getCenO()) {
                            maxC = (float)objT.get(i).getCenO();
                            minC = (float) objT.get(k).getCenO();
                        } else {
                            maxC = (float) objT.get(k).getCenO();
                            minC = (float) objT.get(i).getCenO();
                        }

                        if (objT.get(i).getOts() > objT.get(k).getOts()) {
                            maxO = objT.get(i).getOts();
                            minO = objT.get(k).getOts();
                        } else {
                            maxO = objT.get(k).getOts();
                            minO = objT.get(i).getOts();
                        }

                        getRaschet(bufO, maxC, minC, maxO, minO);
                        getRaschet(bufOp, maxC, minC, maxO, minO);

                        if (bufO.getBalOb() > bufOp.getBalOb()) {
                            bufO.setRang((int) 1);
                            bufOp.setRang((int) 2);
                        } else if (bufO.getBalOb() == bufOp.getBalOb()) {
                            bufO.setRang((int) 1);
                            bufOp.setRang((int) 1);
                        } else {
                            bufO.setRang((int) 2);
                            bufOp.setRang((int) 1);
                        }

                        parSrav.add(bufO);
                        parSrav.add(bufOp);
                    }//if
                }//for k
            }//if
        }//for
        return parSrav;
    }

    private void sortBals(ArrayList<Bal> bals) {
        int pos;
        pos = 0;
        for (int i = 1; i <= bals.get(bals.size() - 1).getLot(); i++) {
            int posN = pos;

            while ( pos < bals.size() && bals.get(pos).getLot() == i && pos < bals.size()) {
                pos+=1;
            }

            for (int a = posN; a < pos-1; a++) {
                for (int b = pos-2; b >= a; b--) {
                    if (bals.get(b).getBalO() < bals.get(b + 1).getBalO()) {

                        Bal bufB = new Bal();
                        bufB.setPos(bals.get(b).getPos());
                        bufB.setLot(bals.get(b).getLot());
                        bufB.setBalO(bals.get(b).getBalO());

                        bals.get(b).setPos(bals.get(b + 1).getPos());
                        bals.get(b).setLot(bals.get(b + 1).getLot());
                        bals.get(b).setBalO(bals.get(b + 1).getBalO());

                        bals.get(b + 1).setPos(bufB.getPos());
                        bals.get(b + 1).setLot(bufB.getLot());
                        bals.get(b + 1).setBalO(bufB.getBalO());

                        /*bals.set(b, bals.get(b + 1));
                        bals.set((b + 1), bufB);*/
                    }
                }
            }

            int r = 1;
            for (int z = posN; z < pos; z++) {
                bals.get(z).setRang(r);
                r++;
            }
        }
    }

    private void writeResultInExcel() {

        XSSFWorkbook workbook = ParseExcel.getSheets(fileJournal);
        Write writeTab = new Write (objTs,parSravs,znachs,workbook);
        writeTab.writeObj();
        writeTab.writePar();
        writeTab.writeZnach();
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(fileJournal);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }//writeResultInExcel

}
