package ch.fcappenzell.gruempeli.administration.tools.tournament.service.email;

import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;

import java.util.*;
import java.io.*;

@Service
public class EmailServiceImpl implements EmailService {

    @Override
    public void display(File emlFile) throws Exception{
        Properties props = System.getProperties();

        Session mailSession = Session.getDefaultInstance(props, null);
        InputStream source = new FileInputStream(emlFile);
        MimeMessage message = new MimeMessage(mailSession);


        System.out.println("Subject : " + message.getSubject());
        System.out.println("From : " + message.getFrom()[0]);
        System.out.println("--------------");
        System.out.println("Body : " +  message.getContent());
    }
}