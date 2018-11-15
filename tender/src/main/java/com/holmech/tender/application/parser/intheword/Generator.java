/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.holmech.tender.application.parser.intheword;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 *
 * @author User
 */
public class Generator {
     String pathForSaving = "D:\\Учеба\\pNetBeans\\WordParserFB\\reports\\FB1.pdf"; 
    String pathForPattern = "D:\\Учеба\\pNetBeans\\WordParserFB\\reports\\FB.jrxml"; 
 
    public void create() throws JRException { 
        DAOStub dataBeanMaker = new DAOStub(); 
        ArrayList<DataBean> dataBeanList = dataBeanMaker.getDataBeanList(); 
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataBeanList); 
        Map<String, Object> parameters = new HashMap<String, Object>(); 
        parameters.put("ministr", "Avos srabotaet"); 
        File reportPattern = new File(pathForPattern); 
        JasperDesign jasperDesign = JRXmlLoader.load(reportPattern); 
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign); 
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, 
                parameters, beanColDataSource); 
        JasperExportManager.exportReportToPdfFile(jasperPrint, pathForSaving); 
    } 
}
