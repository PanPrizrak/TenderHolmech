package com.holmech.tender.application.parser.intheword;

import lombok.NoArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@NoArgsConstructor
public class ProtocolReadService {

    @Value("${template.path}")
    private String templatePath;
    private Map<String,Object> parameters;
    private String templateName;

    public void run(Map<String,Object> parameters, String templateName) throws JRException {
        try {
            this.templateName = templateName;
            this.parameters = parameters;
            this.compile();
            this.fill();
            this.docx();
        } catch (IOException ex) {
            Logger.getLogger(FB.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Начало генерации отчёта");
    }


    public void compile() {
        try {
            long start = System.currentTimeMillis();
            File bsFile = new File(templatePath + templateName + ".jrxml");
            File bsFile2 = new File(templatePath + templateName + "2.jrxml");
            JasperDesign jDesign = (JasperDesign) JRXmlLoader.load(bsFile);
            JasperDesign jDesign2 = (JasperDesign) JRXmlLoader.load(bsFile2);
            JasperCompileManager.compileReportToFile(jDesign, templatePath + templateName + ".jasper");
            JasperCompileManager.compileReportToFile(jDesign2, templatePath + templateName + "2.jasper");
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
            File bsFile2 = new File(templatePath + templateName + "2.jasper");
            JasperReport jReport = (JasperReport) JRLoader.loadObject(bsFile);
            JasperReport jReport2 = (JasperReport) JRLoader.loadObject(bsFile2);
            JasperPrint pageOne =  JasperFillManager.fillReport(jReport, parameters,beanColDataSource);
            JasperPrint pageOther = JasperFillManager.fillReport(jReport2,parameters);
            List pages = pageOther.getPages();
            for (int j = 0; j < pages.size(); j++) {
                JRPrintPage object = (JRPrintPage)pages.get(j);
                pageOne.addPage(object);
            }
            JRSaver.saveObject(pageOne, templatePath + templateName + ".jrprint");
            System.err.println(templatePath + templateName + ".jasper" + "!!!Filling time : " + (System.currentTimeMillis() - start));
        } catch (JRException ex) {
            Logger.getLogger(FB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void docx() {
        try {
            long start = System.currentTimeMillis();
            JRDocxExporter exporter = new JRDocxExporter();
            exporter.setExporterInput(new SimpleExporterInput(templatePath + templateName + ".jrprint"));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(templatePath + templateName + ".docx"));
            exporter.exportReport();
            System.err.println("DOCX creation time : " + (System.currentTimeMillis() - start));
        } catch (JRException ex) {
            Logger.getLogger(FB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
