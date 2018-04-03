package de.alarm_monitor.email;

import com.google.inject.Provider;
import de.alarm_monitor.configuration.MainConfiguration;
import de.alarm_monitor.main.SystemInformation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.inject.Inject;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static de.alarm_monitor.exception.ExceptionUtil.logException;


public class EMailList {


    private static final String EMAIL_List_PATH = "email_list.txt";
    private final static Logger log = LogManager.getLogger(EMailList.class);
    private final EMailConfiguration config;
    private final List<String> receivers = new ArrayList<>();
    private final SystemInformation systemInformation;
    private final MainConfiguration mainConfiguration;

    @Inject
    public EMailList(final SystemInformation systemInformation, final Provider<EMailConfiguration> provider, final Provider<MainConfiguration> mainConfigurationProvider) {
        this.systemInformation = systemInformation;
        this.mainConfiguration = mainConfigurationProvider.get();
        config = provider.get();
        loadReceiverList();
    }

    private void sendEmail(final String receiver, final String msg, final String subject, final boolean isHtml) {
        //avoid exceeding of sender limit
        if (mainConfiguration.isBackUp()) {
            try {
                Thread.sleep(3456);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
        final Properties props = new Properties();
            /*props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp-mail.outlook.com");
			props.put("mail.smtp.port", "587");*/

        props.put("mail.smtp.auth", config.smtpAuth());
        props.put("mail.smtp.starttls.enable", config.startTls());
        props.put("mail.smtp.host", config.smtpHost());
        props.put("mail.smtp.port", config.smtpPort());

        final Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(config.username(), config.password());
            }
        });
        try {
            final Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(config.username()));
            message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(receiver));
            message.setSubject(subject);
            //message.setText(msg);
            if (isHtml) {
                message.setContent(msg, "text/html");
            }

            Transport.send(message);
        } catch (final MessagingException e) {
            logException(this.getClass(), "Fehler beim Verschicken der Email", e);
        }
    }


    public void sendAdminEmail(final String receiver, final String message, final String subject, final String filename) {
        //avoid exceeding of sender limit
        if (mainConfiguration.isBackUp()) {
            try {
                Thread.sleep(3456);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }


        final Properties props = new Properties();
        props.put("mail.smtp.auth", config.smtpAuth());
        props.put("mail.smtp.starttls.enable", config.startTls());
        props.put("mail.smtp.host", config.smtpHost());
        props.put("mail.smtp.port", config.smtpPort());

        final Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(config.username(), config.password());
            }
        });

        try {
            final Message email = new MimeMessage(session);
            final MimeMultipart content = new MimeMultipart("mixed");
            final MimeBodyPart text = new MimeBodyPart();
            text.setText(message);
            content.addBodyPart(text);
            if (filename != null) {
                try {
                    final BodyPart messageBodyPart = new MimeBodyPart();
                    messageBodyPart.setDataHandler(
                            new DataHandler(new FileDataSource(filename)));
                    messageBodyPart.setFileName(new File(filename).getName());
                    content.addBodyPart(messageBodyPart);
                } catch (final Exception e) {
                    logException(this.getClass(), "Fehler beim Erstellen des Anhangs", e);
                }
            }
            email.setContent(content);
            email.setFrom(new InternetAddress(config.username()));
            email.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(receiver));
            email.setSubject(subject);
            Transport.send(email);
        } catch (final MessagingException e) {
            logException(this.getClass(), "Fehler beim Verschicken der Email", e);
        }
    }


    private void loadReceiverList() {
        try (BufferedReader in = new BufferedReader(new FileReader(new File(systemInformation.getConfigFolder(), EMAIL_List_PATH)))) {
            final String line = in.readLine();
            final String[] split = line.split(";");
            for (final String s : split) {
                if (s.length() > 2) {
                    log.trace("Adding {} to Recervers", s);
                    receivers.add(s);
                }
            }
        } catch (final IOException e) {
            logException(this.getClass(), "Fehler beim Laden der Empfänger der Email", e);
        }
    }

    public void broadcast(final String msg, final boolean isHtml) {
        final String subject = config.getEmailTopic();
        log.info("Sending Broadcast to Receivers");
        log.trace("EMail-Content\n" + msg);
        final StringBuilder sb = new StringBuilder();
        for (final String receiver : receivers) {
            sb.append(receiver).append(",");
        }
        log.info("Send Email to: " + sb);
        sendEmail(sb.toString(), msg, subject, isHtml);
    }
}
