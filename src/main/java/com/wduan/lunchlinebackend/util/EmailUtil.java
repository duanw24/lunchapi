package com.wduan.lunchlinebackend.util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.SneakyThrows;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Objects;

public class EmailUtil {

    /**
     * Utility method to send simple HTML email
     * @param session
     * @param toEmail
     * @param subject
     * @param body
     */
   /* public static void sendEmail(String type, Session session, String toEmail, String subject, String body){
        try
        {
            MimeMessage msg = new MimeMessage(session);
            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress("warrenduan@gmail.com", "BearBites-NoReply"));

            msg.setReplyTo(InternetAddress.parse("warrenduan@gmail.com", false));

            msg.setSubject(subject, "UTF-8");

            if(type.equals("text/html")) {
                msg.setContent(body, "text/html");
            } else {
                msg.setText(body, "UTF-8");
            }

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            System.out.println("Message is ready");
            Transport.send(msg);

            System.out.println("EMail Sent Successfully!!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }*/


}