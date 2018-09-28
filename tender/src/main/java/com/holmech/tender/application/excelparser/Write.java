/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.holmech.tender.application.excelparser;


import com.holmech.tender.application.entity.Subject;
import com.holmech.tender.application.entity.calculations.ObjT;
import com.holmech.tender.application.entity.calculations.Znach;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.List;

/**
 * @author User
 */
@Data
@NoArgsConstructor
public class Write {

    private ArrayList<ObjT> objTs;
    private ArrayList<ObjT> parSravs;
    private ArrayList<Znach> znachs;
    private XSSFWorkbook workbook;

    public Write(XSSFWorkbook workbook) {
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
            for (int j = 0; j < 5; j++) { //
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
    }//Запись минимальной цены по лоту

    //Запись минимальной цены по лоту
    public void writeSubjetInExcel(List<Subject> subjectList) {

        XSSFSheet sabjSheet;

        if (workbook.getSheet("После вскрытия") != null) {
            sabjSheet = workbook.getSheet("После вскрытия");
        } else {
            sabjSheet = workbook.createSheet("После вскрытия");
        }
        String buf = "№ лота!" +
                "Название предприятия!" +
                "Отсрочка!" +
                "Название продукта!" +
                "Ед. изм.!" +
                "Количество!" +
                "Цена. за ед. с НДС!" +
                "Код ОКРБ!" +
                "Условия поставки";
        String[] bufTopName = buf.split("!");
        for (int i = 0; i <= subjectList.size(); i++) {
            Row row = sabjSheet.createRow(i);
            Subject bufSubject = null;
            if (i > 0) {
                bufSubject = subjectList.get(i - 1);
            }

            for (int j = 0; j < 9; j++) { //
                Cell cell = row.createCell(j);
                switch (j) {
                    case 0:
                        if ((i == 0)) {
                            cell.setCellValue((String) bufTopName[j]);
                        } else {
                            cell.setCellValue((Integer) bufSubject.getNumberS());
                        }
                        break;
                    case 1:
                        if ((i == 0)) {
                            cell.setCellValue((String) bufTopName[j]);
                        } else {
                            cell.setCellValue((String) bufSubject.getApplicant().getNameA());
                        }
                        break;
                    case 2:
                        if ((i == 0)) {
                            cell.setCellValue((String) bufTopName[j]);
                        } else {
                            cell.setCellValue((String) bufSubject.getPayment());
                        }
                        break;
                    case 3:
                        if ((i == 0)) {
                            cell.setCellValue((String) bufTopName[j]);
                        } else {
                            cell.setCellValue((String) bufSubject.getNameS());
                        }
                        break;
                    case 4:
                        if ((i == 0)) {
                            cell.setCellValue((String) bufTopName[j]);
                        } else {
                            cell.setCellValue((String) bufSubject.getUnits());
                        }
                        break;
                    case 5:
                        if ((i == 0)) {
                            cell.setCellValue((String) bufTopName[j]);
                        } else {
                            if (bufSubject.getAmount() != null) {
                                cell.setCellValue((Integer) bufSubject.getAmount());
                            } else {
                                cell.setCellValue(0);
                            }
                        }
                        break;
                    case 6:
                        if ((i == 0)) {
                            cell.setCellValue((String) bufTopName[j]);
                        } else {
                            if (bufSubject.getPrice() != null) {
                                cell.setCellValue((Double) bufSubject.getPrice());
                            } else {
                                cell.setCellValue(0);
                            }
                        }
                        break;
                    case 7:
                        if ((i == 0)) {
                            cell.setCellValue((String) bufTopName[j]);
                        } else {
                            cell.setCellValue((String) bufSubject.getCode());
                        }
                        break;
                    case 8:
                        if ((i == 0)) {
                            cell.setCellValue((String) bufTopName[j]);
                        } else {
                            cell.setCellValue((String) bufSubject.getDelivery());
                        }
                        break;

                }//switch
            }
        }
    }//Запись subject

}
