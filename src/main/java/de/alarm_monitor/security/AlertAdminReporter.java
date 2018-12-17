package de.alarm_monitor.security;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import de.alarm_monitor.configuration.MainConfiguration;
import de.alarm_monitor.email.EMailList;
import de.alarm_monitor.main.SystemInformation;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Singleton
public class AlertAdminReporter {


    private static final Logger logger = LogManager.getLogger(de.alarm_monitor.security.PeriodicalAdminReporter.class);
    private final SystemInformation systemInformation;
    private final EMailList eMailList;
    private final MainConfiguration mainConfiguration;

    @Inject
    protected AlertAdminReporter(final SystemInformation systemInformation, final Provider<MainConfiguration> provider, final EMailList eMailList) {
        this.systemInformation = systemInformation;
        mainConfiguration = provider.get();

        this.eMailList = eMailList;
    }

    public void sendAlertToAdmin(final String message, final Throwable throwable) {
        logger.info("Sende Email zum Admin wegen eines kritischen Problems");
        final File dir = systemInformation.getLoggingFolder();


        final StringBuilder contentSb = new StringBuilder();
        contentSb.append("Folgendes Problem ist aufgtreten:\nNachricht:");
        contentSb.append(message).append("\n");
        if (throwable != null) {
            contentSb.append("Stacktrace:\n").append(ExceptionUtils.getStackTrace(throwable));
        }
        final String emailAdresses = mainConfiguration.getEmailAdmin();
        logger.debug("Sending critical EMail notification to admin");

        final File[] files = dir.listFiles((dir1, name) -> name.equals("alarmmonitor.log"));
        if (files != null) {
            final File log = files[0];
            eMailList.sendAdminEmail(emailAdresses, contentSb.toString(), "KRITISCHER FEHLER Alarmmonitor", log.getAbsoluteFile().getAbsolutePath());
        } else {
            logger.warn("Error while fetching log file in directory {}.", dir);
            eMailList.sendAdminEmail(emailAdresses, contentSb.toString(), "KRITISCHER FEHLER Alarmmonitor", null);
        }
    }

    public void sendAlertToAdmin(final String message) {
        sendAlertToAdmin(message, null);
    }
}
