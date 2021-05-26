/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labweb1.utils;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import labweb1.constants.MyConstants;

/**
 *
 * @author Gabriel Nguyen
 */
public class MailUtils {

    public void openMailConnection( String recipient, String content) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MyConstants.GOOGLE_MAIL_ID, MyConstants.GOOGle_MAIL_PASS); //To change body of generated methods, choose Tools | Templates.
            }
        });
        Message message = prepareMessage(session, recipient, content);
        Transport.send(message);
        System.out.println("Send email Success");
    }

    private static Message prepareMessage(Session session, String recipient,String content) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(MyConstants.GOOGLE_CLIENT_ID));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("LabWeb1 Verification Code");
            message.setText("LabWeb receive a request to use this email address to create a new account\\n" + content +"\\n To finish signing up, enter the code on the verification page");
            return message;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
