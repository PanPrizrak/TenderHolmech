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

    public static List<Subject> readFromExcel(File fileJournal){
        ApplicantService applicantService = new ApplicantService();
        XSSFSheet spreadsheet = ParseExcel.getSheets(fileJournal).getSheet("Результат");
        Iterator<Row> rowIterator = spreadsheet.iterator();
        ArrayList<Subject> subjects = new ArrayList<Subject>();
        int i = 0;
        int bufNum = 0;
        List<Subject> result = new ArrayList<>();
        while (rowIterator.hasNext()) {
            row = (XSSFRow) rowIterator.next();

            Subject bufResultSubject = new Subject();
            if (bufNum > 0 ) {//&& !row.getCell(0).getStringCellValue().contains("№ Лота")
                i = 0;
                Integer numberLot = null;// = new Integer();
                String resultText = new String();
                while (i < bufNum) {
                    switch (i) {
                        case 0:
                            if (row.getCell(i) != null) {
                                bufResultSubject.setNumberS((int) row.getCell(i).getNumericCellValue());
                            } else {
                                bufResultSubject.setNumberS(0);
                            }
                            break;
                        case 1:
                            if (row.getCell(i) != null) {
                                String bufApplicantName = row.getCell(i).getStringCellValue();
                                bufResultSubject.setApplicantNameA(bufApplicantName);
                            }
                            break;
                        case 2:
                            if (row.getCell(i) != null) {
                                bufResultSubject.setPayment(String.valueOf((int) row.getCell(i).getNumericCellValue()));
                            } else {
                                bufResultSubject.setPayment("");
                            }
                            break;
                        case 3:
                            if (row.getCell(i) != null) {
                                bufResultSubject.setNameS(row.getCell(i).getStringCellValue());
                            } else {
                                bufResultSubject.setNameS("");
                            }
                            break;
                        case 4:
                            if (row.getCell(i) != null) {
                                bufResultSubject.setUnits(row.getCell(i).getStringCellValue());
                            } else {
                                bufResultSubject.setUnits("");
                            }
                            break;
                        case 5:
                            if (row.getCell(i) != null) {
                                bufResultSubject.setPrice(row.getCell(i).getNumericCellValue());
                            } else {
                                bufResultSubject.setPrice(0.0);
                            }
                            break;
                        case 6:
                            if (row.getCell(i) != null) {
                                bufResultSubject.setAmount((int) row.getCell(i).getNumericCellValue());
                            } else {
                                bufResultSubject.setAmount(0);
                            }
                            break;
                    }//switch
                    result.add(bufResultSubject);
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