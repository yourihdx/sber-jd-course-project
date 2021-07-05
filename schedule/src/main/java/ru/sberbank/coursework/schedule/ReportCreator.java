package ru.sberbank.coursework.schedule;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.util.ResourceUtils;
import ru.sberbank.coursework.schedule.model.Schedule;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ReportCreator {

    public ReportCreator(){
    }

    public Path createReport(Schedule schedule, String filename) throws JRException, FileNotFoundException {
        String outputFile = "src//main//resources//templates//" + filename + ".pdf";

        JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(schedule.getPayments());

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("CollectionBeanParam", itemsJRBean);
        parameters.put("amount", schedule.getCreditAmount().toString());
        parameters.put("term", Integer.toString(schedule.getCreditTerm()));
        parameters.put("rate", schedule.getPercentRate().toString());
        parameters.put("overpay", schedule.calculateTotalPercent().toString());

        File file = ResourceUtils.getFile("src//main//resources//Schedule.jrxml");
        InputStream input = new FileInputStream(file);
        JasperDesign jasperDesign = JRXmlLoader.load(input);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

        JasperExportManager.exportReportToPdfFile(jasperPrint, outputFile);
        return Paths.get(outputFile);
    }
}
