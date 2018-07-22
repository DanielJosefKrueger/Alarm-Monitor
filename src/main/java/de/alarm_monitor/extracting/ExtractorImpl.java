package de.alarm_monitor.extracting;


import com.google.inject.Provider;
import de.alarm_monitor.configuration.MainConfiguration;
import de.alarm_monitor.main.AlarmFax;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.ArrayList;

import static de.alarm_monitor.exception.ExceptionUtil.logException;

public class ExtractorImpl implements Extractor {

    private static final Logger logger = LogManager.getLogger(ExtractorImpl.class);
    private final Boolean filterOperationResources;
    private final String filter;

    @Inject
    public ExtractorImpl(final Provider<MainConfiguration> mainConfigurationProvider) {

        final MainConfiguration mainConfiguration = mainConfigurationProvider.get();
        if (mainConfiguration.should_filter_einsatzmittel()) {
            if (mainConfiguration.filter_einsatzmittel().length() > 2) {
                filterOperationResources = true;
            } else {
                logger.error("Filter der Einsatzmittel wurde deaktiviert, da kein Filter gesetzt wurde");
                filterOperationResources = false;
            }
        } else {
            filterOperationResources = false;
        }
        this.filter = mainConfiguration.filter_einsatzmittel();
    }


    @Override
    public AlarmFax extractInformation(final String recognizedText) {

        final String[] allLines = recognizedText.split("\n");
        final ArrayList<String> lineList = new ArrayList<>();
        for (final String line : allLines) {
            if (line.length() > 5) {
                lineList.add(line);
            }
        }

        final String[] lines = new String[lineList.size()];
        lineList.toArray(lines);

        final String address = extractAddress(lines);
        final String comment = extractComment(lines);
        final String keyWord = extractKeyword(lines);
        final String reporter = extractReporter(lines);
        final String coordinates = extractCoordinates(lines);
        final String operationResources = extractOperationRessources(lines);
        final String operationNumber = extractOperationNumber(lines);
        final String alarmTime = extractAlarmTime(lines);


        //for information
        logger.debug("Grund: " + keyWord);
        logger.debug("Adresse: " + address);
        logger.debug("Koordidnate : " + coordinates);
        logger.debug("Bemerkung: " + comment);
        logger.debug("Mittel: " + operationResources);


        final AlarmFax alarmFax = new AlarmFax();
        alarmFax.setKeyword(keyWord);
        alarmFax.setAddress(address);
        alarmFax.setOperationRessources(operationResources);
        alarmFax.setAlarmTime(alarmTime);
        alarmFax.setOperatioNumber(operationNumber);
        alarmFax.setReporter(reporter);
        alarmFax.setComment(comment);
        return alarmFax;
    }


    private String extractReporter(final String[] lines) {
        final StringBuilder sb = new StringBuilder();
        try {
            for (final String line : lines) {
                final String[] splitted = line.split(" ");
                if (splitted.length < 1) { // if no element is in the array
                    continue;
                }
                if (splitted[0].contains("Name")) {
                    sb.append(line);
                }
            }
            return removeNewLineAtEnd(sb.toString());
        } catch (final Exception e) {
            logException(this.getClass(), "Fehler beim Suchen des Mitteilers", e);
            return removeNewLineAtEnd(sb.toString());
        }
    }


    private String extractComment(final String[] lines) {
        final StringBuilder sb = new StringBuilder();
        try {
            boolean aignalSeen = false;
            for (final String line : lines) {
                if (aignalSeen) {
                    sb.append(line).append("\n");
                }
                if (line.contains("BEMERKUNG")) {
                    aignalSeen = true;
                }
            }
            return removeNewLineAtEnd(sb.toString());
        } catch (final Exception e) {
            logException(this.getClass(), "Fehler beim Suchen der Bemerkung", e);
            return removeNewLineAtEnd(sb.toString());
        }
    }


    private String extractKeyword(final String[] lines) {
        final StringBuilder sb = new StringBuilder();
        try {
            for (final String line : lines) {
                final String[] splitted = line.split(" ");
                if (splitted.length < 1) { // if no element is in the array
                    continue;
                }

                if (splitted[0].contains("Schlagw")) {
                    sb.append(line).append("\n");
                }

            }
            return removeBeginTillFirstEmptySpace(removeNewLineAtEnd(sb.toString())).trim();
        } catch (final Exception e) {
            logException(this.getClass(), "Fehler beim Suchen des Schlagwortes", e);
            return removeNewLineAtEnd(sb.toString());
        }
    }

    private String extractAddress(final String[] lines) {
        final StringBuilder sb = new StringBuilder();
        try {
            for (final String line : lines) {
                final String[] splitted = line.split(" ");
                if (splitted.length < 1) { // if no element is in the array
                    continue;
                }

                if (splitted[0].contains("Stra") | splitted[0].contains("Abschnitt") | splitted[0].contains("Ort") | splitted[0].contains("Objekt")) {
                    sb.append(line).append("\n");
                }
            }
            return removeNewLineAtEnd(sb.toString());
        } catch (final Exception e) {
            logException(this.getClass(), "Fehler beim Suchen der Adresse", e);
            return removeNewLineAtEnd(sb.toString());
        }
    }

    private String extractCoordinates(final String[] lines) {
        final StringBuilder sb = new StringBuilder();
        try {
            for (final String line : lines) {
                final String[] splitted = line.split(" ");
                if (splitted.length < 1) { // if no element is in the array
                    continue;
                }
                if (splitted[0].contains("Koordinate")) {
                    sb.append(line).append("\n");
                }

            }
            return sb.toString();
        } catch (final Exception e) {
            logException(this.getClass(), "Fehler beim Suchen der Koordinaten", e);
            return removeNewLineAtEnd(sb.toString());
        }
    }

    private String extractOperationNumber(final String[] lines) {
        final StringBuilder sb = new StringBuilder();
        try {
            for (final String line : lines) {
                final String[] splitted = line.split(" ");
                if (splitted.length < 1) { // if no element is in the array
                    continue;
                }

                if (splitted[0].contains("Einsatznu")) {
                    final int beginAlarmPart = line.indexOf("Alarm");
                    if (beginAlarmPart >= 0) {
                        sb.append(line.subSequence(0, beginAlarmPart));
                    }

                }
            }
            String operationNumber = sb.toString();
            final int index = operationNumber.indexOf(' ');
            if (index >= 0) {
                operationNumber = operationNumber.substring(index, operationNumber.length());
            }
            return operationNumber.trim();
        } catch (final Exception e) {
            logException(this.getClass(), "Fehler beim Suchen der Einsatznummer", e);
            return "";
        }


    }


    private String extractAlarmTime(final String[] lines) {
        final StringBuilder sb = new StringBuilder();
        try {
            for (final String line : lines) {
                final String[] splitted = line.split(" ");
                if (splitted.length < 1) { // if no element is in the array
                    continue;
                }
                if (splitted[0].contains("Einsatznu")) {
                    final int beginAlarmPart = line.indexOf("Alarm");
                    if (beginAlarmPart >= 0) {
                        sb.append(line.subSequence(beginAlarmPart, line.length()));
                    } else {
                        logger.error("In der Einsatznummer-Zeile wurde keine Alarmzeit gefunden");
                    }

                }
            }

        } catch (final Exception e) {
            logException(this.getClass(), "Fehler beim Suchen der Einsatzzeit", e);
        }
        return removeBeginTillFirstEmptySpace(removeNewLineAtEnd(sb.toString())).trim();
    }


    private String extractOperationRessources(final String[] lines) {
        final StringBuilder sb = new StringBuilder();
        try {
            String previousLine = "";
            for (final String line : lines) {
                final String[] splitted = line.split(" ");
                if (splitted.length < 1) { // if no element is in the array
                    continue;
                }
                if (splitted[0].contains("mittel") | (splitted.length > 1 && splitted[1].toLowerCase().contains("ger"))) {

                    if (!filterOperationResources) {
                        sb.append(line).append("\n");
                    } else {
                        if (line.contains(filter)) {
                            sb.append(line).append("\n");
                        }
                        if (line.toLowerCase().contains("ger")) {
                            if (previousLine.contains(filter)) {
                                sb.append(line);
                            }
                        }
                    }
                }
                previousLine = line;
            }
        } catch (final Exception e) {
            logException(this.getClass(), "Fehler beim Suchen der Einsatzmittel", e);
        }

        return removeNewLineAtEnd(sb.toString());
    }


    private String removeNewLineAtEnd(String string) {
        if (string.endsWith("\n")) {
            string = string.substring(0, string.lastIndexOf("\n"));
        }
        return string;
    }


    private String removeBeginTillFirstEmptySpace(final String string) {
        final int index = string.indexOf(" ");
        if (index > 0) {
            return string.substring(index, string.length());
        }
        return string;
    }

}
