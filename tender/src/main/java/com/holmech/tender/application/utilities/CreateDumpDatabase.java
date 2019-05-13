package com.holmech.tender.application.utilities;

import com.smattme.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;


@Service
public class CreateDumpDatabase {

    public void createDump() throws SQLException, IOException, ClassNotFoundException {
        //required properties for exporting of db
        //Properties properties = new Properties();

        Properties properties = new Properties();
        properties.setProperty(MysqlExportService.DB_NAME,"tender");
        properties.setProperty(MysqlExportService.JDBC_DRIVER_NAME,"com.mysql.jdbc.Driver");
        properties.setProperty(MysqlExportService.JDBC_CONNECTION_STRING,"jdbc:mysql://localhost:3307/tender");
        properties.setProperty(MysqlExportService.DB_USERNAME,"root");
        properties.setProperty(MysqlExportService.DB_PASSWORD,"root");
        //properties.setProperty(MysqlExportService.JDBC_DRIVER_NAME,"jdbc");


//properties relating to email config
   /*     properties.setProperty(MysqlExportService.EMAIL_HOST,"smtp.mailtrap.io");
        properties.setProperty(MysqlExportService.EMAIL_PORT,"25");
        properties.setProperty(MysqlExportService.EMAIL_USERNAME,"mailtrap-username");
        properties.setProperty(MysqlExportService.EMAIL_PASSWORD,"mailtrap-password");
        properties.setProperty(MysqlExportService.EMAIL_FROM,"test@smattme.com");
        properties.setProperty(MysqlExportService.EMAIL_TO,"backup@smattme.com");*/

//set the outputs temp dir
        properties.setProperty(MysqlExportService.TEMP_DIR, new File("external").getPath());

        MysqlExportService mysqlExportService = new MysqlExportService(properties);
        mysqlExportService.export();
    }

}
