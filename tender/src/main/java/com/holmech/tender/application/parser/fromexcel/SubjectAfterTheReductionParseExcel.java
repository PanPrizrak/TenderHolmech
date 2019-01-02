package com.holmech.tender.application.parser.fromexcel;

import com.holmech.tender.application.entity.Subject;
import com.holmech.tender.application.entity.SubjectAfterTheReduction;
import com.holmech.tender.application.service.ApplicantService;
import com.holmech.tender.application.service.SubjectService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class SubjectAfterTheReductionParseExcel {


    private static XSSFRow row;

    @Value("${upload.path")
    private static String uploadPath;

    private final SubjectService subjectService;

    public SubjectAfterTheReductionParseExcel(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    public void saveInExcel(List<SubjectAfterTheReduction> subjectAfterTheReductionList, File fileJournal) {

        XSSFWorkbook workbook = ParseExcel.getSheets(fileJournal);
        Write write = new Write(workbook);
        write.writeSubjetAfterTheReductionInExcel(subjectAfterTheReductionList);
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

    public ArrayList<SubjectAfterTheReduction> readFromExcel(File fileJournal){
        ApplicantService applicantService = new ApplicantService();
        XSSFSheet spreadsheet = ParseExcel.getSheets(fileJournal).getSheet("Снижение цены");
        Iterator<Row> rowIterator = spreadsheet.iterator();
        ArrayList<SubjectAfterTheReduction> subjectAfterTheReductionArrayList = new ArrayList<SubjectAfterTheReduction>();
        int i = 0;
        int bufNum = 0;

        while (rowIterator.hasNext()) {
            row = (XSSFRow) rowIterator.next();

            Subject bufSubject = new Subject();
            SubjectAfterTheReduction bufSATR = new SubjectAfterTheReduction();
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
                                String bufApplicantName = row.getCell(i).getStringCellValue();
                                bufSubject.setApplicantNameA(bufApplicantName);
                            }
                            break;
                        case 2:
                            if (row.getCell(i) != null) {//todo
                                bufSATR.setPayment(String.valueOf((int) row.getCell(i).getNumericCellValue()));
                            } else {
                                bufSATR.setPayment("");
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
                                bufSATR.setPrice(row.getCell(i).getNumericCellValue());
                            } else {
                                bufSATR.setPrice(0.0);
                            }
                            break;
                    }//switch
                    bufSubject.setMeet(true);
                    i++;
                }

                Subject subjectFromDB = subjectService.findByApplicantAndNameSAndNumberSAndPrice(bufSubject.getApplicantNameA(),bufSubject.getNameS(),bufSubject.getNumberS(), bufSubject.getPrice());
                bufSATR.setSubject(subjectFromDB);
                subjectAfterTheReductionArrayList.add(bufSATR);
            } else {
                while (row.getCell(i) != null) {
                    bufNum++;
                    i++;
                }
            }
        }
        return subjectAfterTheReductionArrayList;
    }
}
