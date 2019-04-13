package com.holmech.tender.application.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Data
@NoArgsConstructor
@Service
public class CreateZipArchiv {

    File file;
    ZipOutputStream out;

    public void createZip(String[] args) throws Exception {
        /*out = new ZipOutputStream(new FileOutputStream("archive.zip"));

        File file = new File("folder");*/

        doZip(file, out);

        out.close();
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
