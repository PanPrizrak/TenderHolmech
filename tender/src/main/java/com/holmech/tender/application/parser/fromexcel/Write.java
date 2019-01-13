/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.holmech.tender.application.parser.fromexcel;


import com.holmech.tender.application.entity.Subject;
import com.holmech.tender.application.entity.SubjectAfterTheReduction;
import com.holmech.tender.application.entity.calculations.ObjT;
import com.holmech.tender.application.entity.calculations.Znach;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class Write {

    private ArrayList<ObjT> objTs;
    private ArrayList<ObjT> parSravs;
    private ArrayList<Znach> znachs;
    private XSSFWorkbook workbook;

    public Write(XSSFWorkbook workbook) {
        this.workbook = workbook;
    }


    //Запись оценочной таблицы
    public void writeObj(ArrayList<ObjT> objTs) {
        XSSFSheet sheet;
        if (workbook.getSheet("MinCena") != null) {
            sheet = workbook.getSheet("Оценка общая");
        } else {
            sheet = workbook.createSheet("Оценка общая");
        }
        String buf = "№ лота!" +
                "Название предприятия!" +
                "Отсрочка!" +
                "Название продукта!" +
                "Ед. изм.!" +
                "Цена за ед. без НДС!" +
                "Цена сниж!" +
                "Цена окон!" +
                "бал ц!" +
                "бал ц с коф!" +
                "бал отс!" +
                "бал отс с коф!" +
                "общий бал!" +
                "ранг!";
        String[] bufTopName = buf.split("!");
        for (int i = 0;
             i <= objTs.size();
             i++) {
            Row row = sheet.createRow(i);
            ObjT bufO = new ObjT();
            if (i > 0) {
                bufO  = new ObjT(objTs.get(i-1));
            }
            for (int j = 0; j < 14; j++) { // меншье 13 тк кол-во полей ObjT 14
                Cell cell = row.createCell(j);
                switch (j) {
                    case 0:
                        if (i == 0) {
                            cell.setCellValue(bufTopName[j]);
                        } else {
                            cell.setCellValue((int) bufO.getLot());
                        }
                        break;
                    case 1:
                        if (i == 0) {
                            cell.setCellValue(bufTopName[j]);
                        } else {
                            cell.setCellValue((String) bufO.getNameC());
                        }
                        break;
                    case 2:
                        if (i == 0) {
                            cell.setCellValue(bufTopName[j]);
                        } else {
                            cell.setCellValue((int) bufO.getOts());
                        }
                        break;
                    case 3:
                        if (i == 0) {
                            cell.setCellValue(bufTopName[j]);
                        } else {
                            cell.setCellValue((String) bufO.getNameO());
                        }
                        break;
                    case 4:
                        if (i == 0) {
                            cell.setCellValue(bufTopName[j]);
                        } else {
                            cell.setCellValue((String) bufO.getEd());
                        }
                        break;
                    case 5:
                        if (i == 0) {
                            cell.setCellValue(bufTopName[j]);
                        } else {
                            cell.setCellValue((float) bufO.getCen());
                        }
                        break;
                    case 6:
                        if (i == 0) {
                            cell.setCellValue(bufTopName[j]);
                        } else {
                            cell.setCellValue((float) bufO.getCenS());
                        }
                        break;
                    case 7:
                        if (i == 0) {
                            cell.setCellValue(bufTopName[j]);
                        } else {
                            cell.setCellValue((float) bufO.getCenO());
                        }
                        break;
                    case 8:
                        if (i == 0) {
                            cell.setCellValue(bufTopName[j]);
                        } else {
                            cell.setCellValue((float) bufO.getBalC());
                        }
                        break;
                    case 9:
                        if (i == 0) {
                            cell.setCellValue(bufTopName[j]);
                        } else {
                            cell.setCellValue((float) bufO.getBalCk());
                        }
                        break;
                    case 10:
                        if (i == 0) {
                            cell.setCellValue(bufTopName[j]);
                        } else {
                            cell.setCellValue((float) bufO.getBalO());
                        }
                        break;
                    case 11:
                        if (i == 0) {
                            cell.setCellValue(bufTopName[j]);
                        } else {
                            cell.setCellValue((float) bufO.getBalOk());
                        }
                        break;
                    case 12:
                        if (i == 0) {
                            cell.setCellValue(bufTopName[j]);
                        } else {
                            cell.setCellValue((float) bufO.getBalOb());
                        }
                        break;
                    case 13:
                        if (i == 0) {
                            cell.setCellValue(bufTopName[j]);
                        } else {
                            cell.setCellValue(bufO.getRang());
                        }
                        break;
                }//switch
            }
        }
    }

    //Запись попарного сравнения
    public void writePar(ArrayList<ObjT> parSravs) {
        XSSFSheet sheetP;
        if (workbook.getSheet("MinCena") != null) {
            sheetP = workbook.getSheet("Оценка попарная");
        } else {
            sheetP = workbook.createSheet("Оценка попарная");
        }
        String buf = "№ лота!" +
                "Название предприятия!" +
                "Отсрочка!" +
                "Название продукта!" +
                "Ед. изм.!" +
                "Цена за ед. без НДС!" +
                "Цена сниж!" +
                "Цена окон!" +
                "бал ц!" +
                "бал ц с коф!" +
                "бал отс!" +
                "бал отс с коф!" +
                "общий бал!" +
                "ранг!";
        String[] bufTopName = buf.split("!");
        for (int i = 0;
             i <= parSravs.size();
             i++) {
            Row row = sheetP.createRow(i);
            ObjT bufO = new ObjT();
            if (i > 0) {
                bufO = new ObjT(parSravs.get(i-1));
            }
            for (int j = 0; j < 14; j++) { // меншье 13 тк кол-во полей ObjT 14
                Cell cell = row.createCell(j);
                switch (j) {
                    case 0:
                        if (i == 0) {
                            cell.setCellValue(bufTopName[j]);
                        } else {
                            cell.setCellValue((int) bufO.getLot());
                        }
                        break;
                    case 1:
                        if (i == 0) {
                            cell.setCellValue(bufTopName[j]);
                        } else {
                            cell.setCellValue((String) bufO.getNameC());
                        }
                        break;
                    case 2:
                        if (i == 0) {
                            cell.setCellValue(bufTopName[j]);
                        } else {
                            cell.setCellValue((int) bufO.getOts());
                        }
                        break;
                    case 3:
                        if (i == 0) {
                            cell.setCellValue(bufTopName[j]);
                        } else {
                            cell.setCellValue((String) bufO.getNameO());
                        }
                        break;
                    case 4:
                        if (i == 0) {
                            cell.setCellValue(bufTopName[j]);
                        } else {
                            cell.setCellValue((String) bufO.getEd());
                        }
                        break;
                    case 5:
                        if (i == 0) {
                            cell.setCellValue(bufTopName[j]);
                        } else {
                            cell.setCellValue((float) bufO.getCen());
                        }
                        break;
                    case 6:
                        if (i == 0) {
                            cell.setCellValue(bufTopName[j]);
                        } else {
                            cell.setCellValue((float) bufO.getCenS());
                        }
                        break;
                    case 7:
                        if (i == 0) {
                            cell.setCellValue(bufTopName[j]);
                        } else {
                            cell.setCellValue((float) bufO.getCenO());
                        }
                        break;
                    case 8:
                        if (i == 0) {
                            cell.setCellValue(bufTopName[j]);
                        } else {
                            cell.setCellValue((float) bufO.getBalC());
                        }
                        break;
                    case 9:
                        if (i == 0) {
                            cell.setCellValue(bufTopName[j]);
                        } else {
                            cell.setCellValue((float) bufO.getBalCk());
                        }
                        break;
                    case 10:
                        if (i == 0) {
                            cell.setCellValue(bufTopName[j]);
                        } else {
                            cell.setCellValue((float) bufO.getBalO());
                        }
                        break;
                    case 11:
                        if (i == 0) {
                            cell.setCellValue(bufTopName[j]);
                        } else {
                            cell.setCellValue((float) bufO.getBalOk());
                        }
                        break;
                    case 12:
                        if (i == 0) {
                            cell.setCellValue(bufTopName[j]);
                        } else {
                            cell.setCellValue((float) bufO.getBalOb());
                        }
                        break;
                    case 13:
                        if (i == 0) {
                            cell.setCellValue(bufTopName[j]);
                        } else {
                            cell.setCellValue(bufO.getRang());
                        }
                        break;
                }//switch
            }
        }
    }

    //Запись Результата
    public void writeResult(ArrayList<ObjT> resultObjT) {
        XSSFSheet sheetP;
        if (workbook.getSheet("MinCena") != null) {
            sheetP = workbook.getSheet("Результат");
        } else {
            sheetP = workbook.createSheet("Результат");
        }
        String buf = "№ лота!" +
                "Название предприятия!" +
                "Отсрочка!" +
                "Название продукта!" +
                "Ед. изм.!" +
                "Цена за ед. без НДС!" +
                "Количество!";
        String[] bufTopName = buf.split("!");
        for (int i = 0;
             i <= resultObjT.size();
             i++) {
            Row row = sheetP.createRow(i);
            ObjT bufO = new ObjT();
            if (i > 0) {
                bufO = new ObjT(resultObjT.get(i-1));
            }
            for (int j = 0; j < 6; j++) {
                Cell cell = row.createCell(j);
                switch (j) {
                    case 0:
                        if (i == 0) {
                            cell.setCellValue(bufTopName[j]);
                        } else {
                            cell.setCellValue(bufO.getLot());
                        }
                        break;
                    case 1:
                        if (i == 0) {
                            cell.setCellValue(bufTopName[j]);
                        } else {
                            cell.setCellValue(bufO.getNameC());
                        }
                        break;
                    case 2:
                        if (i == 0) {
                            cell.setCellValue(bufTopName[j]);
                        } else {
                            cell.setCellValue(bufO.getOts());
                        }
                        break;
                    case 3:
                        if (i == 0) {
                            cell.setCellValue(bufTopName[j]);
                        } else {
                            cell.setCellValue(bufO.getNameO());
                        }
                        break;
                    case 4:
                        if (i == 0) {
                            cell.setCellValue(bufTopName[j]);
                        } else {
                            cell.setCellValue(bufO.getEd());
                        }
                        break;
                    case 5:
                        if (i == 0) {
                            cell.setCellValue(bufTopName[j]);
                        } else {
                            cell.setCellValue(bufO.getCenO());
                        }
                        break;
                    case 6:
                        if (i == 0) {
                            cell.setCellValue(bufTopName[j]);
                        } else {
                            //cell.setCellValue((float) bufO.getCenS());
                        }
                        break;
                }//switch
            }
        }
    }

    //Запись минимальной цены по лоту
    public void writeZnach(ArrayList<Znach> znachs) {
        XSSFSheet znachM;
        if (workbook.getSheet("MinCena") != null) {
            znachM = workbook.getSheet("MinCena");
        } else {
            znachM = workbook.createSheet("MinCena");
        }
        String buf = "№ лота!" +
                "Цена Макс!" +
                "Цена Мин!" +
                "Отс Макс!" +
                "Отс Мин!";
        String[] bufTopName = buf.split("!");
        for (int i = 0; i <= znachs.size(); i++) {
            Row row = znachM.createRow(i);
            Znach bufO = new Znach();
            if (i > 0) {
                bufO = new Znach(znachs.get(i - 1));
            }
            for (int j = 0; j < 5; j++) { //
                Cell cell = row.createCell(j);
                switch (j) {
                    case 0:
                        if (i == 0) {
                            cell.setCellValue(bufTopName[j]);
                        } else {
                            cell.setCellValue((int) bufO.getNumberLota());
                        }
                        break;
                    case 1:
                        if (i == 0) {
                            cell.setCellValue(bufTopName[j]);
                        } else {
                            cell.setCellValue((float) bufO.getPriceMax());
                        }
                        break;
                    case 2:
                        if (i == 0) {
                            cell.setCellValue(bufTopName[j]);
                        } else {
                            cell.setCellValue((float) bufO.getPriceMin());
                        }
                        break;
                    case 3:
                        if (i == 0) {
                            cell.setCellValue(bufTopName[j]);
                        } else {
                            cell.setCellValue((int) bufO.getDefermentOfPaymentMax());
                        }
                        break;
                    case 4:
                        if (i == 0) {
                            cell.setCellValue(bufTopName[j]);
                        } else {
                            cell.setCellValue((int) bufO.getDefermentOfPaymentMin());
                        }
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


    public void writeSubjetAfterTheReductionInExcel(List<SubjectAfterTheReduction> subjectAfterTheReductionList) {

        XSSFSheet sabjSheet;

        if (workbook.getSheet("Снижение цены") != null) {
            sabjSheet = workbook.getSheet("Снижение цены");
        } else {
            sabjSheet = workbook.createSheet("Снижение цены");
        }
        String buf = "№ лота!" +
                "Название предприятия!" +
                "Отсрочка!" +
                "Название продукта!" +
                "Ед. изм.!" +
                "Количество!" +
                "Цена. за ед. без НДС!" +
                "Сниженная ценна";
        String[] bufTopName = buf.split("!");
        for (int i = 0; i <= subjectAfterTheReductionList.size(); i++) {
            Row row = sabjSheet.createRow(i);
            SubjectAfterTheReduction bufSubjectAfterTheReduction = null;
            if (i > 0) {
                bufSubjectAfterTheReduction = subjectAfterTheReductionList.get(i - 1);
            }

            for (int j = 0; j < 8; j++) { //
                Cell cell = row.createCell(j);
                switch (j) {
                    case 0:
                        if ((i == 0)) {
                            cell.setCellValue((String) bufTopName[j]);
                        } else {
                            cell.setCellValue((Integer) bufSubjectAfterTheReduction.getSubject().getNumberS());
                        }
                        break;
                    case 1:
                        if ((i == 0)) {
                            cell.setCellValue((String) bufTopName[j]);
                        } else {
                            cell.setCellValue((String) bufSubjectAfterTheReduction.getSubject().getApplicant().getNameA());
                        }
                        break;
                    case 2:
                        if ((i == 0)) {
                            cell.setCellValue((String) bufTopName[j]);
                        } else {
                            cell.setCellValue((String) bufSubjectAfterTheReduction.getPayment());
                        }
                        break;
                    case 3:
                        if ((i == 0)) {
                            cell.setCellValue((String) bufTopName[j]);
                        } else {
                            cell.setCellValue((String) bufSubjectAfterTheReduction.getSubject().getNameS());
                        }
                        break;
                    case 4:
                        if ((i == 0)) {
                            cell.setCellValue((String) bufTopName[j]);
                        } else {
                            cell.setCellValue((String) bufSubjectAfterTheReduction.getSubject().getUnits());
                        }
                        break;
                    case 5:
                        if ((i == 0)) {
                            cell.setCellValue((String) bufTopName[j]);
                        } else {
                            if (bufSubjectAfterTheReduction.getSubject().getAmount() != null) {
                                cell.setCellValue((Integer) bufSubjectAfterTheReduction.getSubject().getAmount());
                            } else {
                                cell.setCellValue(0);
                            }
                        }
                        break;
                    case 6:
                        if ((i == 0)) {
                            cell.setCellValue((String) bufTopName[j]);
                        } else {
                            if (bufSubjectAfterTheReduction.getSubject().getPrice() != null) {
                                cell.setCellValue((Double) bufSubjectAfterTheReduction.getSubject().getPrice());
                            } else {
                                cell.setCellValue(0);
                            }
                        }
                        break;
                    case 7:
                        if ((i == 0)) {
                            cell.setCellValue((String) bufTopName[j]);
                        } else {
                            if (bufSubjectAfterTheReduction.getPrice() != null) {
                                cell.setCellValue((Double) bufSubjectAfterTheReduction.getPrice());
                            } else {
                                cell.setCellValue(0);
                            }
                        }
                        break;
                }//switch
            }
        }
    }//Запись subjectAfterTheReduction

}
