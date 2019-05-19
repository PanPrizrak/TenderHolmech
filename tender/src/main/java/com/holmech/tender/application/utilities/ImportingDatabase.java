package com.holmech.tender.application.utilities;

import com.smattme.MysqlImportService;
import lombok.Data;
import org.hibernate.validator.internal.util.Contracts;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

@Data
@Service
public class ImportingDatabase {
    public void importing()  {
        String sql = null;
        try {
            sql = new String(Files.readAllBytes(Paths.get("E:\\PrograFiles\\TenderHolmech\\external\\19_5_2019_10_26_11_tender_database_dump.sql")));
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            boolean res = MysqlImportService.builder()
                    .setSqlString(sql)
                    .setJdbcDriver("com.mysql.jdbc.Driver")
                    .setJdbcConnString("jdbc:mysql://localhost:3306/backup4j_test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false")
                    .setUsername("root")
                    .setPassword("root")
                    .setDeleteExisting(true)
                    .setDropExisting(true)
                    .importDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //Contracts.assertTrue(res);
    }
}
