package com.holmech.tender.application.parser.fromexcel;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParseExcel {

    public static XSSFWorkbook getSheets(File fileJournal) {
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(new FileInputStream(fileJournal));
        } catch (IOException ex) {
            Logger.getLogger(RatingTableParserExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return workbook;
    }
}
