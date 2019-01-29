package de.alarm_monitor.email;

import com.google.inject.Provider;
import de.alarm_monitor.main.SystemInformation;
import org.apache.commons.mail.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static de.alarm_monitor.exception.ExceptionUtil.logException;


public class EMailList {


    private static final String EMAIL_List_PATH = "email_list.txt";
    private final static Logger log = LogManager.getLogger(EMailList.class);
    private final EMailConfiguration config;
    private final List<String> receivers = new ArrayList<>();
    private final SystemInformation systemInformation;

    @Inject
    public EMailList(final SystemInformation systemInformation, final Provider<EMailConfiguration> provider) {
        this.systemInformation = systemInformation;
        config = provider.get();
        loadReceiverList();
    }


    private void sendEmail(final String[] receiver, final String msg, final String subject, final boolean isHtml) {
        if (isHtml) {
            sendHtmlEmail(receiver, msg, subject);
        } else {
            sendNormalEmail(receiver, msg, subject);
        }
    }

    private void sendNormalEmail(final String[] receiver, final String msg, final String subject) {
        try {
            final Email email = new SimpleEmail();
            email.setHostName(config.smtpHost());
            email.setSmtpPort(config.smtpPort());
            email.setAuthenticator(new DefaultAuthenticator(config.username(), config.password()));
            email.setSSLOnConnect(true);
            email.setFrom(config.username());
            email.setSubject(subject);
            email.setMsg(msg);
            email.addTo(receiver);
            email.send();
        } catch (final Exception e) {
            logException(this.getClass(), "Fehler beim Verschicken der Email", e);
        }
    }


    private void sendHtmlEmail(final String[] receiver, final String msg, final String subject) {
        try {
            HtmlEmail email = new HtmlEmail();
            email.setHostName(config.smtpHost());
            email.setSmtpPort(config.smtpPort());
            email.setAuthenticator(new DefaultAuthenticator(config.username(), config.password()));
            email.setSSLOnConnect(true);
            email.setFrom(config.username());
            email.setSubject(subject);
            email.setHtmlMsg(msg);
            email.setTextMsg("Your client does not support HTML unfortunately");
            email.addTo(receiver);
            email.send();
        } catch (final Exception e) {
            logException(this.getClass(), "Fehler beim Verschicken der Email", e);
        }
    }




    public void sendAdminEmail(final String receiver, final String msg, final String subject, final String filename) {
        try {


            final EmailAttachment attachment = new EmailAttachment();
            attachment.setPath(filename);
            attachment.setDisposition(EmailAttachment.ATTACHMENT);
            attachment.setDescription("logfile");
            final MultiPartEmail email = new MultiPartEmail();
            email.attach(attachment);
            email.setHostName(config.smtpHost());
            email.setSmtpPort(config.smtpPort());
            email.setAuthenticator(new DefaultAuthenticator(config.username(), config.password()));
            email.setSSLOnConnect(true);
            email.setFrom(config.username());
            email.setSubject(subject);
            email.setMsg(msg);
            email.addTo(receiver);
            email.send();
        } catch (final Exception e) {
            logException(this.getClass(), "Fehler beim Verschicken der Email", e);
        }
    }


    private void loadReceiverList() {
        try (BufferedReader in = new BufferedReader(new FileReader(new File(systemInformation.getConfigFolder(), EMAIL_List_PATH)))) {
            final String line = in.readLine();
            final String[] split = line.split(";");
            for (final String s : split) {
                if (s.length() > 2) {
                    log.trace("Adding {} to Receivers", s);
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
        log.info("Send Email to: " + receivers);
        sendEmail(receivers.toArray(new String[0]), msg, subject, isHtml);
    }
}
