package com.holmech.tender.application.utilities;

import com.smattme.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;


@Service
public class CreateDumpDatabase {

    @Value("${spring.datasource.username}")
    String userName;

    @Value("${spring.datasource.password}")
    String userPassword;

    @Value("${spring.datasource.url}")
    String datasourceURL;

    @Value("${spring.mail.username}")
    String mailUserName;
    @Value("${spring.mail.password}")
    String mailUserPassword;
    @Value("${spring.mail.host}")
    String mailHost;
    @Value("${spring.mail.protocol}")
    String mailProtocol;
    @Value("${spring.mail.port}")
    String mailPort;



    public File createDump() throws SQLException, IOException, ClassNotFoundException {
        //required properties for exporting of db
        //Properties properties = new Properties();

        Properties properties = new Properties();
        properties.setProperty(MysqlExportService.DB_NAME,"tender");
        properties.setProperty(MysqlExportService.JDBC_DRIVER_NAME,"com.mysql.jdbc.Driver");
        properties.setProperty(MysqlExportService.JDBC_CONNECTION_STRING,datasourceURL);
        properties.setProperty(MysqlExportService.DB_USERNAME,userName);
        properties.setProperty(MysqlExportService.DB_PASSWORD,userPassword);
        properties.setProperty(MysqlExportService.PRESERVE_GENERATED_ZIP, "true");


//properties relating to email config
        /*properties.setProperty(MysqlExportService.EMAIL_HOST,mailHost);
        properties.setProperty(MysqlExportService.EMAIL_PORT,"587");
        properties.setProperty(MysqlExportService.EMAIL_USERNAME,mailUserName);
        properties.setProperty(MysqlExportService.EMAIL_PASSWORD,mailUserPassword);
        properties.setProperty(MysqlExportService.EMAIL_FROM,mailUserName);
        properties.setProperty(MysqlExportService.EMAIL_TO,"webfbtest@yandex.ru");*/

//set the outputs temp dir
        System.out.println(new File("external").getPath());
        properties.setProperty(MysqlExportService.TEMP_DIR, new File("external").getPath());

        MysqlExportService mysqlExportService = new MysqlExportService(properties);
        mysqlExportService.export();
        return mysqlExportService.getGeneratedZipFile();
    }

}
