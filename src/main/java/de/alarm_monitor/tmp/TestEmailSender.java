package de.alarm_monitor.tmp;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;


public class TestEmailSender {


    public static void main(String[] args) {
        sendEmail3("djkrueger13@hotmail.de", "Dies ist eine Testmail", "TestMail", false);
    }


/*
    private static void sendEmail(final String receiver, final String msg, final String subject, final boolean isHtml) {



        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", false);
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.host", "h21.fisinger.de");
        props.put("mail.smtp.port", 465);
        props.put("mail.smtp.connectiontimeout", "5000");
        props.put("mail.smtp.timeout", "5000");

        final Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("alarmfax@feuerwehr-gangkofen.de"  , "1qay2wsx3edc");
            }
        });
        try {
            final Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("alarmfax@feuerwehr-gangkofen.de"));
            message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(receiver));
            message.setSubject(subject);
            //message.setText(msg);
            if (isHtml) {
                message.setContent(msg, "text/html");
            }
            System.out.println("funzt");

            Transport.send(message);
            System.out.println("funzt2");
        } catch (final MessagingException e) {
            e.printStackTrace();
        }
    }

    private static void sendEmail2(final String receiver, final String msg, final String subject, final boolean isHtml) {



        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.host", "smtp.feuerwehr-gangkofen.de");
        props.put("mail.smtp.port", 465);
        props.put("mail.smtp.connectiontimeout", "5000");
        props.put("mail.smtp.timeout", "5000");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("alarmfax@feuerwehr-gangkofen.de","1qay2wsx3edc");
                    }
                });

        try {
            final Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("alarmfax@feuerwehr-gangkofen.de"));
            message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(receiver));
            message.setSubject(subject);
            //message.setText(msg);
            if (isHtml) {
                message.setContent(msg, "text/html");
            }
            System.out.println("funzt");

            Transport.send(message);
            System.out.println("funzt2");
        } catch (final MessagingException e) {
            e.printStackTrace();
        }
    }*/


    private static void sendEmail3(final String receiver, final String msg, final String subject, final boolean isHtml) {
        try {
            Email email = new SimpleEmail();
            String hostName = "h21.fisinger.de";
            email.setHostName(hostName);
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("alarmfax@feuerwehr-gangkofen.de", "1qay2wsx3edc"));
            email.setSSLOnConnect(true);
            email.setFrom("alarmfax@feuerwehr-gangkofen.de");
            email.setSubject("TestMail");
            email.setMsg("This is a test mail ... :-)");
            email.addTo("djkrueger13@hotmail.de");
            email.send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
