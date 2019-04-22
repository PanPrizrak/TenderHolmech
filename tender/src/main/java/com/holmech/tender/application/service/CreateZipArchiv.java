package com.holmech.tender.application.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Data
@NoArgsConstructor
@Service
public class CreateZipArchiv {

    File directory;
    String fileOut;

    public void createZip() throws Exception {

        //TODO
        List<String> arraysFile = new ArrayList<>();
        for (File f : directory.listFiles()) {
            if (f.isDirectory()){}
            else {
                String bufAbsolutePath = f.getAbsolutePath();
                if(bufAbsolutePath.contains("zip")) {
                } else {
                    arraysFile.add(bufAbsolutePath);
                }
            }
        }

        List<String> srcFiles = arraysFile;
        FileOutputStream fos = new FileOutputStream(fileOut);
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        for (String srcFile : srcFiles) {
            File fileToZip = new File(srcFile);
            FileInputStream fis = new FileInputStream(fileToZip);
            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
            zipOut.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            fis.close();
        }
        zipOut.close();
        fos.close();
    }

    public void createZipSaveTree() throws Exception {
        FileOutputStream fos = new FileOutputStream(fileOut);
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        doZip(directory, zipOut);
        zipOut.close();
    }

    private void doZip(File dir, ZipOutputStream out) throws IOException {
        for (File f : dir.listFiles()) {
            if (f.isDirectory())
                doZip(f, out);
            else {
                out.putNextEntry(new ZipEntry(f.getPath()));
                write(new FileInputStream(f), out);
            }
        }
    }

    private void write(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) >= 0)
            out.write(buffer, 0, len);
        in.close();
    }
}
