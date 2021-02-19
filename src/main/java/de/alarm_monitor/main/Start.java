package de.alarm_monitor.main;


import com.google.common.annotations.VisibleForTesting;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;
import de.alarm_monitor.callback.NewPdfCallback;
import de.alarm_monitor.configuration.MainConfiguration;
import de.alarm_monitor.observing.Observer;
import de.alarm_monitor.printing.PrintingService;
import de.alarm_monitor.processing.FaxProcessor;
import de.alarm_monitor.processing.FaxProzessorImpl;
import de.alarm_monitor.security.AlertAdminReporter;
import de.alarm_monitor.security.PeriodicalAdminReporter;
import de.alarm_monitor.util.GraphicUtil;
import de.alarm_monitor.visual.BackUpDisplay;
import de.alarm_monitor.visual.IDisplay;

import de.alarm_monitor.visual.fullscreen.IconLayoutFullScreen;
import de.alarm_monitor.visual.halfscreen.IconLayoutHalfScreen;
import de.alarm_monitor.watcher.ServerSocketHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;


import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Start {
    public static final String VERSION = "1.3.1";
    private static Logger logger;
    static private IDisplay display;
    static private SystemInformation systemInformation;
    static private MainConfiguration mainConfiguration;

//TODO evtl pfad veränderung wenn nicht auas ordner gestartet

    public static void main(final String[] args) {
        printAlarmMonitor();
        final Injector injector = Guice.createInjector(new AlarmMonitorModule());
        systemInformation = injector.getInstance(SystemInformation.class);
        startProcedure(injector);
        logger.info("Der Alarmmonitor startet");
        logger.info("Version: " + VERSION);
        if (mainConfiguration.isBackUp()) {
            printBackupMode();
        }
        final Observer obs = injector.getInstance(Observer.class);
        obs.start();

        final PeriodicalAdminReporter reporter = injector.getInstance(PeriodicalAdminReporter.class);
        final AlertAdminReporter alertAdminReporter = injector.getInstance(AlertAdminReporter.class);
        reporter.start();
        if (!mainConfiguration.isBackUp()) {
            final ServerSocketHandler socketHandler = injector.getInstance(ServerSocketHandler.class);
            socketHandler.start();
        }


        final NewPdfCallback callback = pdf -> {
            new PrintingService(alertAdminReporter, pdf, mainConfiguration).start();
            final FaxProcessor processor = injector.getInstance(FaxProzessorImpl.class);
            processor.processAlarmFax(pdf);
        };
        obs.addCallback(callback);
    }

    public static IDisplay getDisplay() {
        return display;
    }

    @VisibleForTesting
    public static void setDisplay(final IDisplay displayNew) {
        display = displayNew;
    }

    private static void startProcedure(final Injector injector) {
        Configurator.initialize(null, systemInformation.getConfigFolder().toURI().getPath() + "logconfig.xml");
        logger = LogManager.getLogger(Start.class);
        printSystemInformation();
        final Provider<MainConfiguration> provider = injector.getProvider(MainConfiguration.class);

        mainConfiguration = provider.get();
        printConfiouration(mainConfiguration);
        //printTesseractInformation(mainConfiguration);
        if (mainConfiguration.isBackUp()) {
            display = new BackUpDisplay();
        } else {
            display = new IconLayoutHalfScreen();
            GraphicUtil.showOnScreen(mainConfiguration.monitor(), (JFrame) display);
        }
    }


    private static void printSystemInformation() {
        logger.info("Systeminformationen:" +
                ", Projekt-Ordner: " + systemInformation.getProjectDirectory().getAbsolutePath() +
                ", Log-Ordner:" + systemInformation.getLoggingFolder().getAbsolutePath() +

                ", Working-Ordner:" + systemInformation.getWorkingFolder().getAbsolutePath() + "" +
                ", Config-Ordner:" + systemInformation.getConfigFolder().getAbsolutePath());
    }

    private static void printConfiouration(MainConfiguration config){
        logger.info("Konfiguration:\n" +
                "Convert to png:" +config.convertToPng() + "\n"+
                "OCR-Paket: " + config.getOcrPacket() + "\n"+
                "Tesseract Path: " + config.path_tesseract() + "\n"+
                "DPI PNG: " + config.getDpiPng() + "\n" +
                "Einsatzmittel filtern:" + config.should_filter_einsatzmittel() + "\n"+
                "Einsatzmittel Filterwort: " + config.filter_einsatzmittel());
    }

    private static void printTesseractInformation(MainConfiguration mainConfiguration){
        try{
            ProcessBuilder builder = new ProcessBuilder();

            List<String> commands = new ArrayList<>();
            commands.add("\"cd "+ mainConfiguration.path_tesseract() + "\"");

            builder.command(commands);
            Process process = builder.start();
            InputStream inputStream = process.getInputStream();
            StringBuilder sb = new StringBuilder();
            BufferedReader is = new BufferedReader(new InputStreamReader(inputStream));
            is.lines().forEach(sb::append);
            process.waitFor();
            logger.info("Tesseract Informationen: \n" + sb.toString());
        }catch(Throwable t){

            logger.error("Fehler beim Ermitteln der Tesseract Version", t);
        }


    }





    private static void printAlarmMonitor() {
        System.out.println("           _                                            _ _             \n" +
                "     /\\   | |                                          (_) |            \n" +
                "    /  \\  | | __ _ _ __ _ __ ___  _ __ ___   ___  _ __  _| |_ ___  _ __ \n" +
                "   / /\\ \\ | |/ _` | '__| '_ ` _ \\| '_ ` _ \\ / _ \\| '_ \\| | __/ _ \\| '__|\n" +
                "  / ____ \\| | (_| | |  | | | | | | | | | | | (_) | | | | | || (_) | |   \n" +
                " /_/    \\_\\_|\\__,_|_|  |_| |_| |_|_| |_| |_|\\___/|_| |_|_|\\__\\___/|_|   \n" +
                "                                                                        \n" +
                "                                                                        \n" +
                "\n");
    }


    private static void printBackupMode() {
        System.out.println("  ____          _____ _  ___    _ _____    __  __  ____  _____  ______ \n" +
                " |  _ \\   /\\   / ____| |/ / |  | |  __ \\  |  \\/  |/ __ \\|  __ \\|  ____|\n" +
                " | |_) | /  \\ | |    | ' /| |  | | |__) | | \\  / | |  | | |  | | |__   \n" +
                " |  _ < / /\\ \\| |    |  < | |  | |  ___/  | |\\/| | |  | | |  | |  __|  \n" +
                " | |_) / ____ \\ |____| . \\| |__| | |      | |  | | |__| | |__| | |____ \n" +
                " |____/_/    \\_\\_____|_|\\_\\\\____/|_|      |_|  |_|\\____/|_____/|______|\n" +
                "                                                                       \n" +
                "                                                                       \n" +
                "\n");
    }
}



