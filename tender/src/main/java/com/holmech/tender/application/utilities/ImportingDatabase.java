package com.holmech.tender.application.utilities;

import com.smattme.MysqlImportService;
import lombok.Data;
import org.hibernate.validator.internal.util.Contracts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

@Data
@Service
public class ImportingDatabase {

    @Value("${spring.datasource.username}")
    String userName;

    @Value("${spring.datasource.password}")
    String userPassword;

    @Value("${spring.datasource.url}")
    String datasourceURL;

    public void importing(String path)  {
        String sql = null;
        try {
            sql = new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            boolean res = MysqlImportService.builder()
                    .setSqlString(sql)
                    .setJdbcDriver("com.mysql.jdbc.Driver")
                    .setJdbcConnString(datasourceURL)
                    .setUsername(userName)
                    .setPassword(userPassword)
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
