package de.alarm_monitor.processing;

import com.google.inject.Inject;
import com.google.inject.Provider;
import de.alarm_monitor.configuration.MainConfiguration;
import de.alarm_monitor.correcting.TextCorrecter;
import de.alarm_monitor.email.EMailList;
import de.alarm_monitor.exception.*;
import de.alarm_monitor.extracting.Extractor;
import de.alarm_monitor.main.AlarmFax;
import de.alarm_monitor.main.Start;
import de.alarm_monitor.parsing.OCRProcessor;
import de.alarm_monitor.security.AlertAdminReporter;
import de.alarm_monitor.util.AddressFinder;
import de.alarm_monitor.util.AlarmResetter;
import de.alarm_monitor.visual.IDisplay;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

import static de.alarm_monitor.exception.ExceptionUtil.logException;


public class FaxProzessorImpl implements FaxProcessor {

    private static final Logger logger = LogManager.getLogger(FaxProzessorImpl.class);
    private final AlarmResetter alarmResetter;
    private final Boolean shouldSendEmails;

    private final MainConfiguration configuration;
    private final OCRProcessor ocrProcessor;
    private final TextCorrecter correcter;
    private final Extractor extractor;
    private final EMailList queue;
    private final AddressFinder addressFinder;
    private final AlertAdminReporter alertAdminReporter;

    @Inject
    public FaxProzessorImpl(final AlarmResetter alarmResetter,
                            final OCRProcessor ocrProcessor,
                            final TextCorrecter correcter,
                            final Extractor extractor,
                            final EMailList queue,
                            final Provider<MainConfiguration> provider,
                            final AddressFinder addressFinder,
                            final AlertAdminReporter alertAdminReporter) {

        this.addressFinder = addressFinder;
        this.alarmResetter = alarmResetter;
        this.ocrProcessor = ocrProcessor;
        this.correcter = correcter;
        this.extractor = extractor;
        this.queue = queue;
        this.configuration = provider.get();
        this.shouldSendEmails = configuration.isEmailActive();
        this.alertAdminReporter = alertAdminReporter;


    }

    @Override
    public void processAlarmFax(final File pdf) {
        try {
            final String textWithoutCorrection = ocrProcessor.pdfToString(pdf);
            String text = null;
            logger.trace("Parsed Text:\n" + textWithoutCorrection);
            try {
                text = correcter.correct(textWithoutCorrection);
                logger.trace("Created Text:\n" + text);
            } catch (final CorrectingException e) {
                logException(this.getClass(), "Fehler beim Korregieren des eingelesenen Textes, fahre ohne Verbesserung fort", e);
                alertAdminReporter.sendAlertToAdmin("Error while correcting Text", e);
            }


            //if correction failed we use the non corrected text
            if (text == null) {
                text = textWithoutCorrection;
            }
            final AlarmFax alarmFax = extractor.extractInformation(text);


            try {
                updateDisplay(alarmFax);
            } catch (final DisplayChangeException e) {
                alertAdminReporter.sendAlertToAdmin("Error while changing the display", e);
                logException(this.getClass(), "Fehler beim Update der Bildschirmanzeige", e);
            }

            try {
                addLinkToInformation(alarmFax);
            } catch (final LinkCreationException e) {
                alertAdminReporter.sendAlertToAdmin("Error while getting the link for the routing from google", e);
                logException(this.getClass(), "Fehler beim Suchen der Route", e);
            }

            if (shouldSendEmails) {
                sendEmail(alarmFax);
            }
        } catch (final OcrParserException e) {
            logException(this.getClass(), "Fehler beim OCR Vorgang", e);
            alertAdminReporter.sendAlertToAdmin("Error while performing ocr", e);
        } catch (final EMailSendException e) {
            logException(this.getClass(), "Fehler beim Versenden der Emails", e);
            alertAdminReporter.sendAlertToAdmin("Error while sending alarm emails ", e);
        }
    }


    private void updateDisplay(final AlarmFax alarmFax) throws DisplayChangeException {

        alarmResetter.resetAlarm(configuration.getMonitorResetTime());
        try {
            final IDisplay display = Start.getDisplay();
            display.changeAlarmFax(alarmFax);
            display.activateAlarm();
        } catch (final Exception e) {
            throw new DisplayChangeException(e);
        }
    }


    private void sendEmail(final AlarmFax alarmFax) throws EMailSendException {
        try {
            queue.broadcast(alarmFax.toEmailHtml(), true);
        } catch (final Exception e) {
            throw new EMailSendException(e);
        }
    }


    private void addLinkToInformation(final AlarmFax alarmFax) throws LinkCreationException {
        try {
            final String link = addressFinder.createLink(alarmFax.getAddress());
            alarmFax.setLink(link);
        } catch (final Exception e) {
            throw new LinkCreationException(e.getMessage());
        }
    }


}
