package ch.fcappenzell.gruempeli.administration.tools.tournament.service.email;

import com.sun.mail.iap.ByteArray;
import jakarta.activation.FileDataSource;
import jakarta.mail.BodyPart;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.*;
import java.io.*;

@Service
public class EmailServiceImpl implements EmailService {

    @Override
    public void display(File emlFile) throws Exception{

        MimeMultipart mp = new MimeMultipart(new FileDataSource(emlFile));

        int count = mp.getCount();

        //Extracting MIME one by one
        String finalXml = "<finalXML><data>" + mp.getPreamble() + "</data>";
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mp.getBodyPart(i);

            String contentType = bodyPart.getContentType();
            String temp = (String) bodyPart.getContent();
            System.out.println(contentType);
            System.out.println(temp);


            finalXml += "<data>" + temp + "</data>";

            if (contentType.startsWith("text/xml")) {


            } else if (contentType.startsWith("multipart/")) {

                MimeMultipart mp2 = (MimeMultipart) bodyPart.getContent();

                for (int j = 0; j < mp2.getCount(); j++) {
                    BodyPart bodyPart1 = mp2.getBodyPart(j);
                    String contentType1 = bodyPart1.getContentType();
                    if (contentType1.startsWith("text/xml")) {
                        finalXml += "<data>" + bodyPart1.getContent() + "</data>";
                    } else if (contentType1.startsWith("application/pdf")) {
                        finalXml = finalXml + ("<attachment>" + bodyPart1.getContent() + "</attachment>");
                    }
                }
            }
            finalXml += "</finalXML>";
        }


        System.out.println(finalXml);
    }
}