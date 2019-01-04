package com.holmech.tender.application.parser.fromexcel;

import com.holmech.tender.application.entity.Applicant;
import com.holmech.tender.application.entity.Subject;
import com.holmech.tender.application.service.ApplicantService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.util.*;


public class ResultParseExcel {
    private static XSSFRow row;

    @Value("${upload.path")
    private static String uploadPath;

    public static void saveInExcel(List<Subject> subjectList, File fileJournal) {

        XSSFWorkbook workbook = ParseExcel.getSheets(fileJournal);
        Write write = new Write(workbook);
        write.writeSubjetInExcel(subjectList);
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
    }

    public static Map<Integer,String> readFromExcel(File fileJournal){
        ApplicantService applicantService = new ApplicantService();
        XSSFSheet spreadsheet = ParseExcel.getSheets(fileJournal).getSheet("Результат");
        Iterator<Row> rowIterator = spreadsheet.iterator();
        ArrayList<Subject> subjects = new ArrayList<Subject>();
        int i = 0;
        int bufNum = 0;
        Map<Integer,String> result = new HashMap<>();
        while (rowIterator.hasNext()) {
            row = (XSSFRow) rowIterator.next();

            Subject bufSubject = new Subject();
            if (bufNum > 0 ) {//&& !row.getCell(0).getStringCellValue().contains("№ Лота")
                i = 0;
                Integer numberLot = null;// = new Integer();
                String resultText = new String();
                while (i < bufNum) {
                    switch (i) {
                        case 0:
                            if (row.getCell(i) != null) {
                                numberLot = Double.valueOf(row.getCell(i).getNumericCellValue()).intValue();

                            } else {
                                numberLot = 999;
                            }
                            break;
                        case 1:
                            if (row.getCell(i) != null) {
                                resultText = row.getCell(i).getStringCellValue();
                            } else {
                                resultText = "Ошибка в чтении!!";
                            }
                            break;
                    }//switch
                    result.put(numberLot, resultText);
                    i++;
                }
            } else {
                while (row.getCell(i) != null) {
                    bufNum++;
                    i++;
                }
            }
        }
        return result;
    }
}