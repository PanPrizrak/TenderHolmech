/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.holmech.tender.application.parser.intheword;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.jasperreports.engine.*;
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
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
@Service
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Letterhead {

    @Value("${template.path}")
    private String templatePath;
    private Map<String,Object> parameters;
    private String outPath;
    private static String templateName = new String("FBnew");

    public String run(Map<String,Object> parameters, String outPath) throws JRException {
        try {

            this.outPath = outPath;
            this.parameters = parameters;
            this.compile();
            this.fill();
            System.out.println("Начало генерации отчёта");
            return this.docx();
            //this.odt();
            //this.viewer();
        } catch (IOException ex) {
            Logger.getLogger(Letterhead.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    

    public void compile() {
        try {
            long start = System.currentTimeMillis();
            File bsFile = new File(templatePath + templateName + ".jrxml");
            JasperDesign jDesign = (JasperDesign) JRXmlLoader.load(bsFile);
            JasperCompileManager.compileReportToFile(jDesign, templatePath + templateName + ".jasper");
            System.err.println("Compile time : " + (System.currentTimeMillis() - start));
        } catch (JRException e) {
            System.out.println(e);
        }
    }

    public void fill() throws IOException {
        try {
            DAOStub dataBeanMaker = new DAOStub();
            ArrayList<DataBean> dataBeanList = dataBeanMaker.getDataBeanList();
            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataBeanList);
            long start = System.currentTimeMillis();
            File bsFile = new File(templatePath + templateName + ".jasper");
            JasperReport jReport = (JasperReport) JRLoader.loadObject(bsFile);
            JasperFillManager.fillReportToFile(jReport, templatePath + templateName + ".jrprint", parameters,beanColDataSource);
            System.err.println(templatePath + templateName + ".jasper" + "!!!Filling time : " + (System.currentTimeMillis() - start));
        } catch (JRException ex) {
            Logger.getLogger(Letterhead.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String docx() {
        try {
            long start = System.currentTimeMillis();
            JRDocxExporter exporter = new JRDocxExporter();
            exporter.setExporterInput(new SimpleExporterInput(templatePath + templateName + ".jrprint"));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outPath + parameters.get("numberM") + ".docx"));
            exporter.exportReport();
            System.err.println("DOCX creation time : " + (System.currentTimeMillis() - start));
            return templatePath + templateName + parameters.get("numberM") + ".docx";
        } catch (JRException ex) {
            Logger.getLogger(Letterhead.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void odt() {
        try {
            long start = System.currentTimeMillis();
            JROdtExporter exporter = new JROdtExporter();
            exporter.setExporterInput(new SimpleExporterInput(templatePath + templateName + ".jrprint"));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(templatePath + templateName + ".odt"));
            exporter.exportReport();
            System.err.println("ODT creation time : " + (System.currentTimeMillis() - start));
        } catch (JRException ex) {
            Logger.getLogger(Letterhead.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void viewer() {
        try {
            //отображение jrprint
            long start = System.currentTimeMillis();
            File sourceFile = new File(templatePath + templateName + ".jrprint");
            JasperPrint jasperPrint = (JasperPrint) JRLoader.loadObject(sourceFile);
            JasperViewer.viewReport(jasperPrint, false);
            //JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            //jasperViewer.setVisible(true);
            System.err.println("Viewer creation time : " + (System.currentTimeMillis() - start));
        } catch (JRException ex) {
            Logger.getLogger(Letterhead.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
