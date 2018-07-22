package de.alarm_monitor.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static de.alarm_monitor.exception.ExceptionUtil.logException;

public class FileUtil {

    public static String getLastLinesOfFile(final int number, final File file) {

        try (BufferedReader in = new BufferedReader(new FileReader(file))) {

            final List<String> list = new ArrayList<>();
            in.lines().forEach(list::add);


            final int begin = list.size() - number > 0 ? list.size() - number : 0;
            final List<String> sublist = list.subList(begin, list.size());
            final StringBuilder sb = new StringBuilder();
            for (final String s : sublist) {
                sb.append(s).append("\n");
            }
            return sb.toString();
        } catch (final Exception e) {
            logException(FileUtil.class, "Fehler beim Erstellen des Logs", e);
            return "Fehler beim Erstellen des Logs";
        }
    }
}
