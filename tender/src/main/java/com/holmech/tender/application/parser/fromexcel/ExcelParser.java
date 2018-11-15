/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.holmech.tender.application.parser.fromexcel;


import com.holmech.tender.application.entity.Applicant;
import com.holmech.tender.application.entity.calculations.Bal;
import com.holmech.tender.application.entity.calculations.ObjT;
import com.holmech.tender.application.entity.calculations.Znach;
import com.holmech.tender.application.repository.ApplicantRepository;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExcelParser {

    static XSSFRow row;
    private static float cenaK = (float) 0.8;
    private static float otsK = (float) 0.2;

    @Autowired
    private ApplicantRepository applicantReposirory;

    private static void getRaschet(ObjT t, float maxC, float minC, int maxO, int minO) {

        if (maxC != minC) {
            t.setBalC((float) (1 + (maxC - t.getCenO()) / (maxC - minC) * 9));
        } else {
            t.setBalC((float) 1.0);
        }
        t.setBalCk((float) t.getBalC() * cenaK);

        if (maxO != minO) {
            float b1;
            float b2;
            float b3;
            b1 = (float) t.getOts() - minO;
            b2 = (float) maxO - minO;
            b3 = (float) b1 / b2 * 9;
            t.setBalO((float) (1 + b3));
        } else {
            t.setBalO((float) 1.0);
        }
        t.setBalOk((float) t.getBalO() * otsK);

        t.setBalOb((float) t.getBalOk() + t.getBalCk());
        
        float balCk = (float) t.getBalC() * cenaK;
        float balOk = (float) t.getBalO() * otsK;
        if (balCk < 0 || balCk > 10 || balOk < 0 || balOk > 10 )
            JOptionPane.showMessageDialog(null, t.toString()+"!!!"+maxC+"!!!"+minC+"!!!"+maxO+"!!!"+minO);
    }

    public static void parse() throws FileNotFoundException {

        //String result = "";
        FileInputStream fis = null;

        JFileChooser window = new JFileChooser();
        int returnValue = window.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            fis = new FileInputStream(window.getSelectedFile());
        }

        //JOptionPane.showMessageDialog(null, window.getSelectedFile().toString());
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(fis);
        } catch (IOException ex) {
            Logger.getLogger(ExcelParser.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Заполнение из таблицы экселя
        XSSFSheet spreadsheet = workbook.getSheetAt(3);

        Iterator< Row> rowIterator = spreadsheet.iterator();
        ArrayList<ObjT> objT = new ArrayList<>();
        ObjT buf;

        while (rowIterator.hasNext()) {
            row = (XSSFRow) rowIterator.next();
            buf = new ObjT();
            for (int i = 0; i < 8; i++) {
                switch (i) {
                    case 0:
                        buf.setLot((int) row.getCell(i).getNumericCellValue());
                        break;
                    case 1:
                        if (row.getCell(i) != null) {
                            buf.setNameC(row.getCell(i).getStringCellValue());
                        } else {
                            buf.setNameC("");
                        }
                        break;
                    case 2:
                        if (row.getCell(i) != null) {
                            buf.setOts((int) row.getCell(i).getNumericCellValue());
                        } else {
                            buf.setOts((int) 0);
                        }
                        break;
                    case 3:
                        if (row.getCell(i) != null) {
                            buf.setNameO(row.getCell(i).getStringCellValue());
                        } else {
                            buf.setNameO("");
                        }
                        break;
                    case 4:
                        if (row.getCell(i) != null) {
                            buf.setEd(row.getCell(i).getStringCellValue());
                        } else {
                            buf.setEd("");
                        }
                        break;
                    case 5:
                        if (row.getCell(i) != null) {
                            buf.setCen((float) row.getCell(i).getNumericCellValue());
                        } else {
                            buf.setCen((float) 0.0);
                        }
                        break;
                    case 6:
                        if (row.getCell(i) != null) {
                            buf.setCenS((float) row.getCell(i).getNumericCellValue());
                        } else {
                            buf.setCenS((float) 0.0);
                        }
                        break;
                    case 7:
                        if (row.getCell(i) != null) {
                            buf.setCenO((float) row.getCell(i).getNumericCellValue());
                        } else {
                            buf.setCenO((float) 0.0);
                        }
                        break;
                }

            }
            objT.add(buf);
        }

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

            znach = new Znach((int) (i + 1), maxC, minC, maxO, minO);
            znachs.add(znach);

        }

        //Расчет балов
        for (int i = 0; i < objT.size(); i++) {

            int maxO = znachs.get(objT.get(i).getLot() - 1).getOtsMax();
            int minO = znachs.get(objT.get(i).getLot() - 1).getOtsMin();
            float maxC = znachs.get(objT.get(i).getLot() - 1).getCenaMax();
            float minC = znachs.get(objT.get(i).getLot() - 1).getCenaMin();
            if (objT.get(i).getCenO() != 0) {
                getRaschet(objT.get(i), maxC, minC, maxO, minO);
            }
        }

        //попарное сравнение
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

        //Заполнение объеекта Bal
        ArrayList<Bal> bals = new ArrayList<Bal>();
        for (int i = 0; i < objT.size(); i++) {
            if (objT.get(i).getCenO() != 0) {
                Bal bal = new Bal();
                bal.setPos(i);
                bal.setLot(objT.get(i).getLot());
                bal.setBalO(objT.get(i).getBalOb());
                bals.add(bal);
            }
        }

        //сортировка  в каждом лоте по убыванию общих балов
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

        //присвоение рангов
        for (int i = 0; i < bals.size(); i++) {
            objT.get(bals.get(i).getPos()).setRang((int) bals.get(i).getRang());
        }

        Write writeTab = new Write ();

        writeTab.writeObj();
        writeTab.writePar();
        writeTab.writeZnach();

        //workbook = writeTab.getWorkbook();

        try {
            FileOutputStream outputStream = new FileOutputStream(window.getSelectedFile());
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Done");

        try {
            fis.close();
        } catch (IOException ex) {
            Logger.getLogger(ExcelParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//!!!!!!!!!!!!!!!End parse


    public void parseJournal(File fileJournal) {
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(fileJournal);
        } catch (IOException ex) {
            Logger.getLogger(ExcelParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

        //Заполнение из таблицы экселя
        XSSFSheet spreadsheet = workbook.getSheetAt(1);

        Iterator<Row> rowIterator = spreadsheet.iterator();

        int i = 0;
        int bufNum = 0;

        while (rowIterator.hasNext()) {
            row = (XSSFRow) rowIterator.next();



            Applicant bufApplicant = new Applicant();
            if (bufNum > 0 && !row.getCell(0).getStringCellValue().contains("№ Лота")) {
                i = 0;
                ArrayList<Integer> appliclots = new ArrayList<>();
                while (i < bufNum) {

                    if (i == 0) {
                        bufApplicant.setIdA((long) 1);
                        bufApplicant.setNameA(row.getCell(i).getStringCellValue());
                        bufApplicant.setAddress("");
                        bufApplicant.setPan("");
                        bufApplicant.setContactsSet(Collections.emptySet());
                        bufApplicant.setWorkerSet(Collections.emptySet());
                    } else {
                        if (row.getCell(i) != null)
                            appliclots.add((int) row.getCell(i).getNumericCellValue());
                        else
                            appliclots.add((int) 0);
                    }
                    i++;
                    System.out.println(appliclots.size());
                }

             applicantReposirory.save(bufApplicant);
            } else {
                while (row.getCell(i) != null) {
                    bufNum++;
                    i++;
                }
            }
        }
    }//end parseJournal
}
