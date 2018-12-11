package com.holmech.tender.application.parser.fromexcel;

import com.holmech.tender.application.entity.Applicant;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class ApplicantParseExcel {
    private static XSSFRow row;

    public static ArrayList<Applicant> parse( File fileJournal) {

        XSSFWorkbook workbook = ParseExcel.getSheets(fileJournal);

        //Заполнение из таблицы экселя TODO
        XSSFSheet spreadsheet = workbook.getSheetAt(1);

        Iterator<Row> rowIterator = spreadsheet.iterator();
        ArrayList<Applicant> applicants = new ArrayList<Applicant>();
        int i = 0;
        int bufNum = 0;

        while (rowIterator.hasNext()) {
            row = (XSSFRow) rowIterator.next();

            Applicant bufApplicant = new Applicant();
            if (bufNum > 0) {
                i = 0;
                ArrayList<Integer> appliclots = new ArrayList<>();
                while (i < bufNum) {

                    if (i == 0) {
                        bufApplicant.setNameA(row.getCell(i).getStringCellValue());
                    } else {
                        if (row.getCell(i) != null)
                            appliclots.add((int) row.getCell(i).getNumericCellValue());
                        else
                            appliclots.add((int) 0);
                    }
                    i++;
                }
                bufApplicant.setLots(appliclots);
                applicants.add(bufApplicant);
            } else {
                while (row.getCell(i) != null) {
                    bufNum++;
                    i++;
                }
            }
        }
        return applicants;
    }//end parseJournal

}
