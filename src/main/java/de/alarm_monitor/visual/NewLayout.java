package de.alarm_monitor.visual;

import de.alarm_monitor.main.AlarmFax;
import de.alarm_monitor.util.LayoutCalculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewLayout extends JFrame implements IDisplay {


    private final static String HTML_BEGIN = "<html><FONT SIZE=\"7\" font-family=\"sans-serif\"><b>";
    private final static String OPERATIONNUMBER_DEFAULT = HTML_BEGIN + "EinsatzNummer: </b>";
    private final static String ALARMTIME_DEFAULT = HTML_BEGIN + "Alarm Zeit: </b>";
    private final static String REPORTER_DEFAULT = HTML_BEGIN + "Mitteiler: </b>";
    private final static String KEYWORD_DEFAULT = HTML_BEGIN + "Schlagwort: </b>";
    private final static String COMMENT_DEFAULT = HTML_BEGIN + "Bemerkung: </b><br><FONT SIZE=\"6\">";
    private final static String ADRESSE_DEFAULT = HTML_BEGIN + "Adresse: </b><br> <FONT SIZE=\"6\">";
    private final static String OPERATIONRESSOURCES_DEFAULT = HTML_BEGIN + "Einsatzmittel: </b><br><FONT SIZE=\"6\">";
    private static Font FONT = new Font(Font.SANS_SERIF, Font.BOLD, 20);


    private final JTextPane sectionOperationNumber;
    private final JTextPane sectionOperationTime;
    private final JTextPane sectionKeyWord;
    private final JTextPane sectionReporter;
    private final JTextPane sectionAddress;
    private final JTextPane sectionOperationRessources;
    private final JTextPane sectionComment;
    private final JButton resetButton;

    private boolean alarmActive;

    public NewLayout() {

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        this.getContentPane().setLayout(null);
        this.setLayout(null);
        this.setSize(dim);
        this.getContentPane().setBackground(Color.white);
        JPanel body = new JPanel();
        body.setBounds(20, 20, (int) dim.getWidth() - 40, (int) dim.getHeight() - 40);
        body.setLayout(null);
        body.setBackground(Color.white);
        body.setOpaque(true);
        this.add(body);

        LayoutCalculator calculator = new LayoutCalculator(body.getWidth(), body.getHeight(), 2, 10);


        sectionOperationNumber = new JTextPane();
        sectionOperationNumber.setContentType("text/html");

        sectionOperationNumber.setText(OPERATIONNUMBER_DEFAULT);
        sectionOperationNumber.setBounds(calculator.getRectangleForPosition(0, 0, 1, 1));
        sectionOperationNumber.setFont(FONT);
        body.add(sectionOperationNumber);


        sectionOperationTime = new JTextPane();
        sectionOperationTime.setContentType("text/html");
        sectionOperationTime.setText(ALARMTIME_DEFAULT);
        sectionOperationTime.setBounds(calculator.getRectangleForPosition(1, 0, 1, 1));
        sectionOperationTime.setFont(FONT);
        body.add(sectionOperationTime);


        sectionKeyWord = new JTextPane();
        sectionKeyWord.setContentType("text/html");
        sectionKeyWord.setText(KEYWORD_DEFAULT);
        sectionKeyWord.setBounds(calculator.getRectangleForPosition(0, 1, 1, 1));
        body.add(sectionKeyWord);


        sectionReporter = new JTextPane();
        sectionReporter.setContentType("text/html");
        sectionReporter.setText(REPORTER_DEFAULT);
        sectionReporter.setBounds(calculator.getRectangleForPosition(1, 1, 1, 1));
        body.add(sectionReporter);


        sectionAddress = new JTextPane();
        sectionAddress.setContentType("text/html");
        sectionAddress.setText(ADRESSE_DEFAULT);
        sectionAddress.setBounds(calculator.getRectangleForPosition(0, 2, 1, 2));
        body.add(sectionAddress);


        sectionOperationRessources = new JTextPane();
        sectionOperationRessources.setContentType("text/html");
        sectionOperationRessources.setText(OPERATIONRESSOURCES_DEFAULT);
        sectionOperationRessources.setBounds(calculator.getRectangleForPosition(1, 2, 1, 7));
        body.add(sectionOperationRessources);


        resetButton = new JButton("Alarm zurücksetzen");

        resetButton.setBounds(calculator.getRectangleForPosition(1, 9, 0.5, 0.5));
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                resetAlarm();
            }
        });
        body.add(resetButton);


        sectionComment = new JTextPane();
        sectionComment.setContentType("text/html");
        sectionComment.setText(COMMENT_DEFAULT);

        sectionComment.setBounds(calculator.getRectangleForPosition(0, 4, 2, 6));

        //   sectionComment.setLineWrap(true);
        body.add(sectionComment);


        this.setUndecorated(true);
        this.setAlwaysOnTop(false);
        this.setVisible(true);
    }


    public static void main(String... args) {

        NewLayout newLayout = new NewLayout();
        newLayout.changeComment("hallo\nhallo\nhallo\n" +
                "123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789" +
                "\n123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789");
        newLayout.changeOperationNumber("<html>test");
        newLayout.changeAddress("Musterstadt\nMusterStraße\nMusterHaus");
        newLayout.changeOperationRessources("Einsatzmittel 1\nEinsatzmittel 2\nEinsatzmittel 3\n" +
                "Einsatzmittel 1\nEinsatzmittel 2\nEinsatzmittel 3\n" +
                "Einsatzmittel 1\nEinsatzmittel 2\nEinsatzmittel 3\n" +
                "Einsatzmittel 1\nEinsatzmittel 2\nEinsatzmittel 3\n" +
                "Einsatzmittel 1\nEinsatzmittel 2\nEinsatzmittel 3\n");


    }


    @Override
    public void changeReporter(String name) {
        sectionReporter.setText(REPORTER_DEFAULT + name);
    }

    @Override
    public void changeOperationNumber(String operationNumber) {
        sectionOperationNumber.setText(OPERATIONNUMBER_DEFAULT + operationNumber);
    }

    @Override
    public void changeAlarmTime(String alarmTime) {
        sectionOperationTime.setText(ALARMTIME_DEFAULT + " " + alarmTime);
    }

    @Override
    public void changeKeyWord(String keyWord) {
        sectionKeyWord.setText(KEYWORD_DEFAULT + " " + keyWord);
    }

    @Override
    public void changeComment(String comment) {
        comment = wrapLinesManually(comment);
        comment = replaceNewLineWithHtmlTag(comment);
        sectionComment.setText(COMMENT_DEFAULT + comment);
    }

    @Override
    public void changeAddress(String adresse) {
        adresse = replaceNewLineWithHtmlTag(adresse);
        sectionAddress.setText(ADRESSE_DEFAULT + adresse);
    }

    @Override
    public void changeOperationRessources(String operationRessources) {
        operationRessources = replaceNewLineWithHtmlTag(operationRessources);
        sectionOperationRessources.setText(OPERATIONRESSOURCES_DEFAULT + operationRessources);
    }

    @Override
    public void resetAlarm() {
        sectionReporter.setText(REPORTER_DEFAULT);
        sectionOperationNumber.setText(OPERATIONNUMBER_DEFAULT);
        sectionOperationTime.setText(ALARMTIME_DEFAULT);
        sectionKeyWord.setText(KEYWORD_DEFAULT);
        sectionComment.setText(COMMENT_DEFAULT);
        sectionAddress.setText(ADRESSE_DEFAULT);
        sectionOperationRessources.setText(OPERATIONRESSOURCES_DEFAULT);
        alarmActive = false;
        getContentPane().setBackground(Color.white);
    }

    @Override
    public void activateAlarm() {
        alarmActive = true;
        Runnable runnable = () -> {
            long start = System.currentTimeMillis();
            while (System.currentTimeMillis() < start + 5 * 60 * 1000) {
                if (!alarmActive) {
                    break;
                }
                getContentPane().setBackground(Color.red);
                refresh();
                try {
                    Thread.sleep(500);
                    getContentPane().setBackground(Color.white);
                    refresh();
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void refresh() {
        repaint();
    }

    @Override
    public void changeAlarmFax(AlarmFax alarmFax) {
        changeReporter(alarmFax.getReporter());
        changeOperationNumber(alarmFax.getOperationNumber());
        changeAlarmTime(alarmFax.getAlarmTime());
        changeKeyWord(alarmFax.getKeyword());
        changeComment(alarmFax.getComment());
        changeAddress(alarmFax.getAddress());
        changeOperationRessources(alarmFax.getOperationRessources());
    }


    private static String replaceNewLineWithHtmlTag(String s) {
        return s.replaceAll("\n", "<br>");
    }


    private static String wrapLinesManually(String s) {

        StringBuilder ret = new StringBuilder(s);
        int length = s.length();
        int indexLine = 0;
        for (int i = 0; i < length; i++) {
            char c = ret.charAt(i);
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

