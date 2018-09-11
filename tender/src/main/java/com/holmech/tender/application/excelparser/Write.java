/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.holmech.tender.application.excelparser;



import java.util.ArrayList;

import com.holmech.tender.application.entity.calculations.ObjT;
import com.holmech.tender.application.entity.calculations.Znach;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author User
 */
public class Write {

    private ArrayList<ObjT> objTs;
    private ArrayList<ObjT> parSravs;
    private ArrayList<Znach> znachs;
    private XSSFWorkbook workbook;

    public Write() {
    }

    public Write(ArrayList<ObjT> objTs, ArrayList<ObjT> bals, ArrayList<Znach> znachs, XSSFWorkbook workbook) {
        this.objTs = objTs;
        this.parSravs = bals;
        this.znachs = znachs;
        this.workbook = workbook;
    }

    public ArrayList<ObjT> getObjTs() {
        return objTs;
    }

    public void setObjTs(ArrayList<ObjT> objTs) {
        this.objTs = objTs;
    }

    public ArrayList<ObjT> getParSravs() {
        return parSravs;
    }

    public void setParSravs(ArrayList<ObjT> parSravs) {
        this.parSravs = parSravs;
    }

    public ArrayList<Znach> getZnachs() {
        return znachs;
    }

    public void setZnachs(ArrayList<Znach> znachs) {
        this.znachs = znachs;
    }

    public XSSFWorkbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(XSSFWorkbook workbook) {
        this.workbook = workbook;
    }
    
    //Запись оценочной таблицы
    public void writeObj() {
        XSSFSheet sheet = workbook.createSheet("Оценка общая");
        for (int i = 0;
                i < objTs.size();
                i++) {
            Row row = sheet.createRow(i);
            ObjT bufO = new ObjT(objTs.get(i));
            for (int j = 0; j < 14; j++) { // меншье 13 тк кол-во полей ObjT 14
                Cell cell = row.createCell(j);
                switch (j) {
                    case 0:
                        cell.setCellValue((int) bufO.getLot());
                        break;
                    case 1:
                        cell.setCellValue((String) bufO.getNameC());
                        break;
                    case 2:
                        cell.setCellValue((int) bufO.getOts());
                        break;
                    case 3:
                        cell.setCellValue((String) bufO.getNameO());
                        break;
                    case 4:
                        cell.setCellValue((String) bufO.getEd());
                        break;
                    case 5:
                        cell.setCellValue((float) bufO.getCen());
                        break;
                    case 6:
                        cell.setCellValue((float) bufO.getCenS());
                        break;
                    case 7:
                        cell.setCellValue((float) bufO.getCenO());
                        break;
                    case 8:
                        cell.setCellValue((float) bufO.getBalC());
                        break;
                    case 9:
                        cell.setCellValue((float) bufO.getBalCk());
                        break;
                    case 10:
                        cell.setCellValue((float) bufO.getBalO());
                        break;
                    case 11:
                        cell.setCellValue((float) bufO.getBalOk());
                        break;
                    case 12:
                        cell.setCellValue((float) bufO.getBalOb());
                        break;
                    case 13:
                        cell.setCellValue(bufO.getRang());
                        break;
                }//switch
            }
        }
    }

    //Запись попарного сравнения
    public void writePar() {
        XSSFSheet sheetP = workbook.createSheet("Оценка попарная");
        for (int i = 0;
                i < parSravs.size();
                i++) {
            Row row = sheetP.createRow(i);
            ObjT bufO = new ObjT(parSravs.get(i));
            for (int j = 0; j < 14; j++) { // меншье 13 тк кол-во полей ObjT 14
                Cell cell = row.createCell(j);
                switch (j) {
                    case 0:
                        cell.setCellValue((int) bufO.getLot());
                        break;
                    case 1:
                        cell.setCellValue((String) bufO.getNameC());
                        break;
                    case 2:
                        cell.setCellValue((int) bufO.getOts());
                        break;
                    case 3:
                        cell.setCellValue((String) bufO.getNameO());
                        break;
                    case 4:
                        cell.setCellValue((String) bufO.getEd());
                        break;
                    case 5:
                        cell.setCellValue((float) bufO.getCen());
                        break;
                    case 6:
                        cell.setCellValue((float) bufO.getCenS());
                        break;
                    case 7:
                        cell.setCellValue((float) bufO.getCenO());
                        break;
                    case 8:
                        cell.setCellValue((float) bufO.getBalC());
                        break;
                    case 9:
                        cell.setCellValue((float) bufO.getBalCk());
                        break;
                    case 10:
                        cell.setCellValue((float) bufO.getBalO());
                        break;
                    case 11:
                        cell.setCellValue((float) bufO.getBalOk());
                        break;
                    case 12:
                        cell.setCellValue((float) bufO.getBalOb());
                        break;
                    case 13:
                        cell.setCellValue(bufO.getRang());
                        break;
                }//switch
            }
        }
    }

    //Запись минимальной цены по лоту
    public void writeZnach() {
        XSSFSheet znachM = workbook.createSheet("MinCena");
        for (int i = 0; i < znachs.size(); i++) {
            Row row = znachM.createRow(i);
            Znach bufO = new Znach(znachs.get(i));
            for (int j = 0; j < 5; j++) { // меншье 13 тк кол-во полей ObjT 14
                Cell cell = row.createCell(j);
                switch (j) {
                    case 0:
                        cell.setCellValue((int) bufO.getNomL());
                        break;
                    case 1:
                        cell.setCellValue((float) bufO.getCenaMax());
                        break;
                    case 2:
                        cell.setCellValue((float) bufO.getCenaMin());
                        break;
                    case 3:
                        cell.setCellValue((int) bufO.getOtsMax());
                        break;
                    case 4:
                        cell.setCellValue((int) bufO.getOtsMin());
                        break;
                }//switch
            }
        }
    }

}
