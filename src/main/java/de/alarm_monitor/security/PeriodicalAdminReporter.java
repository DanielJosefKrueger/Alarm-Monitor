package de.alarm_monitor.security;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import de.alarm_monitor.configuration.MainConfiguration;
import de.alarm_monitor.email.EMailList;
import de.alarm_monitor.main.SystemInformation;
import de.alarm_monitor.util.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.concurrent.TimeUnit;

@Singleton
public class PeriodicalAdminReporter extends Thread {
    private static final Logger logger = LogManager.getLogger(AlertAdminReporter.class);
    private final SystemInformation systemInformation;
    private final EMailList eMailList;
    private final MainConfiguration mainConfiguration;

    @Inject
    PeriodicalAdminReporter(final SystemInformation systemInformation, final Provider<MainConfiguration> provider, final EMailList eMailList) {
        this.systemInformation = systemInformation;
        mainConfiguration = provider.get();

        this.eMailList = eMailList;
    }


    @Override
    public void run() {
        logger.debug("Starting Adminreporter");
        while (true) {
            try {
                final File dir = systemInformation.getLoggingFolder();
                final File[] files = dir.listFiles((dir1, name) -> name.equals("alarmmonitor.log"));
                final File log;
                if (files != null) {
                    log = files[0];
                    final String content = createEmailForAdmin();
                    final String emailAdresses = mainConfiguration.getEmailAdmin();
                    logger.debug("Sending regular notification to admin");
                    // EMailList.sendEmail(emailAdresses, content, "Status Alarmmonitor");
                    eMailList.sendAdminEmail(emailAdresses, content, "Status Alarmmonitor", log.getAbsoluteFile().getAbsolutePath());
                } else {
                    logger.warn("Could not find log file for sending to the admin in directory {}", dir);
                }
                TimeUnit.MINUTES.sleep(mainConfiguration.getIntervalEmailAdmin());
            } catch (final Exception e) {
                logger.error("Ein Fehler ist beim Versenden der Periodischen Email an den Admin aufgetreten", e);
            }
        }
    }


    private String createEmailForAdmin() {

        final File dir = systemInformation.getLoggingFolder();
        final File[] files = dir.listFiles((dir1, name) -> name.equals("alarmmonitor.log"));
        if (files != null) {
            final File log = files[0];
            return FileUtil.getLastLinesOfFile(200, log);
        }
        logger.error("Konnte Logdatei im Ordner {} nicht finden", dir);
        return "Fehler: Log-Datei konnte nicht gefunden werden";
    }
}
