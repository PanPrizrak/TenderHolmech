/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.holmech.tender.application.parser.intheword;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.view.JasperViewer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author User
 */
public class FB {

    @Value("${upload.path}")
    private String sr;// = "D:\\Учеба\\pNetBeans\\WordParserFB\\reports\\";


    public run() throws JRException {
        try {
            FB buf = new FB();
            String b;
            b = "FB";
            buf.compile(b);
            buf.fill(b);
            buf.docx(b);
            buf.odt(b);
            buf.viewer(b);
        } catch (IOException ex) {
            Logger.getLogger(FB.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Начало генерации отчёта");
        /*try {
            new Generator().create();
            System.out.println("Генерация отчёта завершена");
        } catch (Exception e) {
            System.out.println("Во время генерации возникла ошибка: " + e);
        }*/
    }
    

    public void compile(String s_n) {
        try {
            long start = System.currentTimeMillis();
            File bsFile = new File(sr + s_n + ".jrxml");
            JasperDesign jDesign = (JasperDesign) JRXmlLoader.load(bsFile);
            JasperCompileManager.compileReportToFile(jDesign, sr + s_n + ".jasper");
            System.err.println("Compile time : " + (System.currentTimeMillis() - start));
        } catch (JRException e) {
            System.out.println(e);
        }
    }

    public void fill(String s_n) throws IOException {
        try {
            DAOStub dataBeanMaker = new DAOStub();
            ArrayList<DataBean> dataBeanList = dataBeanMaker.getDataBeanList();
            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataBeanList);
            long start = System.currentTimeMillis();

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("ministr", sender.toSender());
            parameters.put("name", "");

            File bsFile = new File(sr + s_n + ".jasper");
            JasperReport jReport = (JasperReport) JRLoader.loadObject(bsFile);
            JasperFillManager.fillReportToFile(jReport, sr + s_n + ".jrprint", parameters,beanColDataSource);
            System.err.println(sr + s_n + ".jasper" + "!!!Filling time : " + (System.currentTimeMillis() - start));
        } catch (JRException ex) {
            Logger.getLogger(FB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void docx(String s_n) {
        try {
            long start = System.currentTimeMillis();
            JRDocxExporter exporter = new JRDocxExporter();
            exporter.setExporterInput(new SimpleExporterInput(sr + s_n + ".jrprint"));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(sr + s_n + ".docx"));
            exporter.exportReport();
            System.err.println("DOCX creation time : " + (System.currentTimeMillis() - start));
        } catch (JRException ex) {
            Logger.getLogger(FB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void odt(String s_n) {
        try {
            long start = System.currentTimeMillis();
            JROdtExporter exporter = new JROdtExporter();
            exporter.setExporterInput(new SimpleExporterInput(sr + s_n + ".jrprint"));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(sr + s_n + ".odt"));
            exporter.exportReport();
            System.err.println("ODT creation time : " + (System.currentTimeMillis() - start));
        } catch (JRException ex) {
            Logger.getLogger(FB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void viewer(String s_n) {
        try {
            //отображение jrprint
            long start = System.currentTimeMillis();
            File sourceFile = new File(sr + s_n + ".jrprint");
            JasperPrint jasperPrint = (JasperPrint) JRLoader.loadObject(sourceFile);
            JasperViewer.viewReport(jasperPrint, false);
            System.err.println("Viewer creation time : " + (System.currentTimeMillis() - start));
        } catch (JRException ex) {
            Logger.getLogger(FB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
