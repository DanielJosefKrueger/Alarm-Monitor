package de.alarm_monitor.visual;

import de.alarm_monitor.main.AlarmFax;
import de.alarm_monitor.util.LayoutCalculator;
import de.alarm_monitor.util.StringUtils;

import javax.swing.*;
import java.awt.*;

public class IconLayout extends JFrame implements IDisplay{

    private final static String HTML_BEGIN = "<html><FONT SIZE=\"7\" font-family=\"sans-serif\"><b>";
    private final static String ALARMTIME_DEFAULT = HTML_BEGIN + "Alarm Zeit: </b>";
    private final static String KEYWORD_DEFAULT = HTML_BEGIN + "Schlagwort: </b>";
    private final static String COMMENT_DEFAULT = HTML_BEGIN + "Bemerkung: </b><br><FONT SIZE=\"6\">";
    private final static String ADRESSE_DEFAULT = HTML_BEGIN + "Adresse: </b><br> <FONT SIZE=\"6\">";
    private static final Font FONT = new Font(Font.SANS_SERIF, Font.BOLD, 20);


    private final JTextPane sectionOperationTime;
    private final JTextPane sectionKeyWord;
    private final JTextPane sectionAddress;
    private final JTextPane sectionComment;
    private final JButton resetButton;
    private final VerticalIconPane verticalIconPane;

    private boolean alarmActive;

        public IconLayout() {

            final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

            this.getContentPane().setLayout(null);
            this.setLayout(null);
            this.setSize(dim);
            this.getContentPane().setBackground(Color.white);
            final JPanel body = new JPanel();
            body.setBounds(20, 20, (int) dim.getWidth() - 40, (int) dim.getHeight() - 40);
            body.setLayout(null);
            body.setBackground(Color.white);
            body.setOpaque(true);
            this.add(body);

            final LayoutCalculator calculator = new LayoutCalculator(body.getWidth(), body.getHeight(), 3, 10);



            Rectangle rectangleIcons = calculator.getRectangleForPosition(0, 0, 1, 10);
            VerticalIconPane verticalIconPane = new VerticalIconPane(rectangleIcons);
            verticalIconPane.setBounds(rectangleIcons);
            body.add(verticalIconPane);
            this.verticalIconPane = verticalIconPane;


            sectionOperationTime = new JTextPane();
            sectionOperationTime.setContentType("text/html");
            sectionOperationTime.setText(ALARMTIME_DEFAULT);
            sectionOperationTime.setBounds(calculator.getRectangleForPosition(1, 0, 1, 1));
            sectionOperationTime.setFont(FONT);
            body.add(sectionOperationTime);


            sectionKeyWord = new JTextPane();
            sectionKeyWord.setContentType("text/html");
            sectionKeyWord.setText(KEYWORD_DEFAULT);
            sectionKeyWord.setBounds(calculator.getRectangleForPosition(1, 1, 1, 1));
            body.add(sectionKeyWord);



            sectionAddress = new JTextPane();
            sectionAddress.setContentType("text/html");
            sectionAddress.setText(ADRESSE_DEFAULT);
            sectionAddress.setBounds(calculator.getRectangleForPosition(1, 2, 1, 2));
            body.add(sectionAddress);


            sectionComment = new JTextPane();
            sectionComment.setContentType("text/html");
            sectionComment.setText(COMMENT_DEFAULT);
            sectionComment.setBounds(calculator.getRectangleForPosition(1, 4, 1, 5));



            resetButton = new JButton("Alarm zurücksetzen");
            resetButton.setBounds(calculator.getRectangleForPosition(1, 9, 0.5, 0.5));
            resetButton.addActionListener(arg0 -> resetAlarm());
            body.add(resetButton);




            //   sectionComment.setLineWrap(true);
            body.add(sectionComment);


            this.setUndecorated(true);
            this.setAlwaysOnTop(false);
            this.setVisible(true);
        }


    public static void main(final String... args) {
        String operationResources = "Einsatzmittelname : 2.2.4 PAN FF Gangkofen\n" +
                "gef. Geräte\n" +
                "Einsatzmittelname : 2.2.4 PAN FL Gangkofen 31/1\n" +
                "gef. Geräte\n" +
                "Einsatzmittelname : 2.2.4 PAN KBM FL RI 4/2 Aschl\n" +
                "gef. Geräte\n" +
                "Einsatzmittelname : 2.2.4 PAN FL I?Dienst Rottal/Inn\n" +
                "gef. Geräte";



        final IconLayout iconLayout = new IconLayout();
        iconLayout.changeKeyWord("TestKexword");
        iconLayout.changeComment("hallo\nhallo\nhallo\n" +
                "123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789" +
                "\n123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789");
        iconLayout.changeOperationNumber("<html>test");
        iconLayout.changeAddress("Musterstadt\nMusterStraße\nMusterHaus");
        iconLayout.changeOperationRessources(operationResources);
        iconLayout.changeAlarmTime("TEstAlarmzeit");
        iconLayout.activateAlarm(operationResources);
    }



    @Override
    public void changeReporter(final String name) {
        //npt shown
    }

    @Override
    public void changeOperationNumber(final String operationNumber) {
        //not shown
    }

    @Override
    public void changeAlarmTime(final String alarmTime) {
        sectionOperationTime.setText(ALARMTIME_DEFAULT + " " + alarmTime);
    }

    @Override
    public void changeKeyWord(final String keyWord) {
        sectionKeyWord.setText(KEYWORD_DEFAULT + " " + keyWord);
    }

    @Override
    public void changeComment(String comment) {
        comment = StringUtils.wrapLinesManually(comment);
        comment = StringUtils.replaceNewLineWithHtmlTag(comment);
        sectionComment.setText(COMMENT_DEFAULT + comment);
    }

    @Override
    public void changeAddress(String adresse) {
        adresse = StringUtils.replaceNewLineWithHtmlTag(adresse);
        sectionAddress.setText(ADRESSE_DEFAULT + adresse);
    }

    @Override
    public void changeOperationRessources(String operationRessources) {
        //not shown
    }

    @Override
    public void resetAlarm() {
        sectionOperationTime.setText(ALARMTIME_DEFAULT);
        sectionKeyWord.setText(KEYWORD_DEFAULT);
        sectionComment.setText(COMMENT_DEFAULT);
        sectionAddress.setText(ADRESSE_DEFAULT);
        alarmActive = false;
        getContentPane().setBackground(Color.white);
        verticalIconPane.unHighlightRequestedResources();
    }

    @Override
    public void activateAlarm(String operationResources) {
        alarmActive = true;
        final Runnable runnable = () -> {
            final long start = System.currentTimeMillis();
            verticalIconPane.highlightRequestedResources(operationResources);
            while (System.currentTimeMillis() < start + 5 * 60 * 1000) {
                if (!alarmActive) {
                    return; // arm has been reset, so get out of this Thread

                }
                getContentPane().setBackground(Color.red);
                refresh();
                try {
                    Thread.sleep(500);
                    getContentPane().setBackground(Color.white);

                    refresh();
                    Thread.sleep(500);
                } catch (final InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        final Thread thread = new Thread(runnable);
        thread.start();
    }

    private void refresh() {
        repaint();
    }

    @Override
    public void changeAlarmFax(final AlarmFax alarmFax) {
        changeReporter(alarmFax.getReporter());
        changeOperationNumber(alarmFax.getOperationNumber());
        changeAlarmTime(alarmFax.getAlarmTime());
        changeKeyWord(alarmFax.getKeyword());
        changeComment(alarmFax.getComment());
        changeAddress(alarmFax.getAddress());
        changeOperationRessources(alarmFax.getOperationRessources());
    }




}
