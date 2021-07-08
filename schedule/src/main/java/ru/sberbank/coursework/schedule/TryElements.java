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
import java.util.HashMap;
import java.util.Map;

public class TryElements {
    public static void main(String[] args) throws FileNotFoundException, JRException {

        ReportCreator reportCreator = new ReportCreator();
        Schedule schedule = new Schedule(
                new BigDecimal(1000000),
                30,
                new BigDecimal(7),
                true
        );
        Path path = reportCreator.createReport(schedule, "classreport");
        System.out.println(path);
    }
}
