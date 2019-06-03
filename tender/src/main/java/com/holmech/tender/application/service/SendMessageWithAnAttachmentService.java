package com.holmech.tender.application.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;

@Service
public class SendMessageWithAnAttachmentService {

    @Value("${spring.mail.username}")
    private String username;

    private final JavaMailSender emailSender;

    public SendMessageWithAnAttachmentService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendAttachmentEmail(String setTo, String subjectText, String messageText, List<String> attachments) throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();

        boolean multipart = true;

        MimeMessageHelper helper = new MimeMessageHelper(message, multipart);

        helper.setTo(setTo);
        helper.setSubject(subjectText);

        helper.setText(messageText);


        for(String pathFile: attachments) {
            int slash = pathFile.lastIndexOf('/');
            int backSlash = pathFile.lastIndexOf('\\');

            String nameFileAtaachment = new String();

            if(slash>=0 && backSlash<0){
                nameFileAtaachment = pathFile.substring(slash+1);
            } else {
                nameFileAtaachment = pathFile.substring(backSlash+1);
            }

            FileSystemResource fileAttachment = new FileSystemResource(new File(pathFile));
            helper.addAttachment(nameFileAtaachment, fileAttachment);
        }
        helper.setFrom(username);

        emailSender.send(message);
    }
}
