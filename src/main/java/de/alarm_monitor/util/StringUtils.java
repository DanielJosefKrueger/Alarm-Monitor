package de.alarm_monitor.util;

public class StringUtils{
        public static String replaceNewLineWithHtmlTag(final String s) {
            return s.replaceAll("\n", "<br>");
        }

        public static String wrapLinesManually(final String s) {

            final StringBuilder ret = new StringBuilder(s);
            int length = s.length();
            int indexLine = 0;
            for (int i = 0; i < length; i++) {
                final char c = ret.charAt(i);
                if (c == '\n') {
                    indexLine = 0;
                } else {
                    indexLine++;
                    if (indexLine >= 60) {
                        ret.insert(i + 1, '\n');
                        indexLine = 0;
                        i++;
                        length++; //the word is longer now!
                    }
                }
            }
            return ret.toString();
        }


}
