package com.holmech.tender.application.excelparser;

import com.holmech.tender.application.entity.Subject;
import com.holmech.tender.application.entity.Tender;
import com.holmech.tender.application.repository.ApplicantRepository;
//import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SubjectParseExcel {

    private static ApplicantRepository applicantRepository;
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

    public static ArrayList<Subject> readFromExcel(File fileJournal, Tender tender){
        XSSFSheet spreadsheet = ParseExcel.getSheets(fileJournal).getSheet("После вскрытия");
        Iterator<Row> rowIterator = spreadsheet.iterator();
        ArrayList<Subject> subjects = new ArrayList<Subject>();
        int i = 0;
        int bufNum = 0;

        while (rowIterator.hasNext()) {
            row = (XSSFRow) rowIterator.next();

            Subject bufSubject = new Subject();
            if (bufNum > 0 ) {//&& !row.getCell(0).getStringCellValue().contains("№ Лота")
                i = 0;
                while (i < bufNum) {
                  switch (i) {
                        case 0:
                            if (row.getCell(i) != null) {
                                bufSubject.setNumberS((int) row.getCell(i).getNumericCellValue());
                            } else {
                                bufSubject.setNumberS(0);
                            }
                            break;
                        case 1:
                            if (row.getCell(i) != null) {
                                bufSubject.setApplicant(applicantRepository.findByNameA(row.getCell(i).getStringCellValue()));
                            }
                            break;
                        case 2:
                            if (row.getCell(i) != null) {
                                bufSubject.setPayment(row.getCell(i).getStringCellValue());
                            } else {
                                bufSubject.setPayment("");
                            }
                            break;
                        case 3:
                            if (row.getCell(i) != null) {
                                bufSubject.setNameS(row.getCell(i).getStringCellValue());
                            } else {
                                bufSubject.setNameS("");
                            }
                            break;
                        case 4:
                            if (row.getCell(i) != null) {
                                bufSubject.setUnits(row.getCell(i).getStringCellValue());
                            } else {
                                bufSubject.setUnits("");
                            }
                            break;
                        case 5:
                            if (row.getCell(i) != null) {
                                bufSubject.setAmount((int) row.getCell(i).getNumericCellValue());
                            } else {
                                bufSubject.setAmount(0);
                            }
                            break;
                        case 6:
                            if (row.getCell(i) != null) {
                                bufSubject.setPrice(row.getCell(i).getNumericCellValue());
                            } else {
                                bufSubject.setPrice(0.0);
                            }
                            break;
                        case 7:
                            if (row.getCell(i) != null) {
                                bufSubject.setCode(row.getCell(i).getStringCellValue());
                            } else {
                                bufSubject.setCode("");
                            }
                            break;
                        case 8:
                            if (row.getCell(i) != null) {
                                bufSubject.setDelivery(row.getCell(i).getStringCellValue());
                            } else {
                                bufSubject.setDelivery("");
                            }
                            break;
                    }//switch
                    bufSubject.setTender(tender);
                    i++;
                }
                subjects.add(bufSubject);
            } else {
                while (row.getCell(i) != null) {
                    bufNum++;
                    i++;
                }
            }
        }
        return subjects;
    }
}
