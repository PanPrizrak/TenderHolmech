package com.holmech.tender.application.excelparser;

import com.holmech.tender.application.entity.Applicant;
import com.holmech.tender.application.entity.Subject;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApplicantParseExcel {
    private static XSSFRow row;

    public static ArrayList<Applicant> parse( File fileJournal) {

        XSSFWorkbook workbook = getSheets(fileJournal);

        //Заполнение из таблицы экселя
        XSSFSheet spreadsheet = workbook.getSheetAt(1);

        Iterator<Row> rowIterator = spreadsheet.iterator();
        ArrayList<Applicant> applicants = new ArrayList<Applicant>();
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

    private static XSSFWorkbook getSheets(File fileJournal) {
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(fileJournal);
        } catch (IOException ex) {
            Logger.getLogger(ExcelParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        return workbook;
    }

    public static void saveInExcel(List<Subject> subjectList, File fileJournal) throws IOException {
        XSSFWorkbook workbook = getSheets(fileJournal);
        Write write = new Write(workbook);
        write.writeSubjetInExcel(subjectList);
        FileOutputStream outputStream = new FileOutputStream(fileJournal);
        workbook.write(outputStream);
        workbook.close();
    }
}
