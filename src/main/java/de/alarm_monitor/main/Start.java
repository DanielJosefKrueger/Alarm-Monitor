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
import de.alarm_monitor.visual.NewLayout;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import javax.swing.*;

public class Start {
    private static Logger logger;
    static private IDisplay display;
    static private SystemInformation systemInformation;
    static private MainConfiguration mainConfiguration;

//TODO evtl pfad veränderung wenn nicht auas ordner gestartet

    public static void main(String[] args) {
        printAlarmMonitor();
        Injector injector = Guice.createInjector(new AlarmMonitorModule());
        systemInformation = injector.getInstance(SystemInformation.class);
        startProcedure(injector);
        logger.info("Der Alarmmonitor startet");
        logger.info("Version: 1.0.1");
        if (mainConfiguration.isBackUp()) {
            printBackupMode();
        }
        Observer obs = injector.getInstance(Observer.class);
        obs.start();

        final PeriodicalAdminReporter reporter = injector.getInstance(PeriodicalAdminReporter.class);
        final AlertAdminReporter alertAdminReporter = injector.getInstance(AlertAdminReporter.class);
        reporter.start();


        NewPdfCallback callback = pdf -> {
            new PrintingService(alertAdminReporter, pdf, mainConfiguration).start();
            FaxProcessor processor = injector.getInstance(FaxProzessorImpl.class);
            processor.processAlarmFax(pdf);
        };
        obs.addCallback(callback);
    }

    public static IDisplay getDisplay() {
        return display;
    }

    @VisibleForTesting
    public static void setDisplay(IDisplay displayNew) {
        display = displayNew;
    }

    private static void startProcedure(Injector injector) {
        Configurator.initialize(null, systemInformation.getConfigFolder().toURI().getPath() + "logconfig.xml");
        logger = LogManager.getLogger(Start.class);
        printConfiguration();
        Provider<MainConfiguration> provider = injector.getProvider(MainConfiguration.class);
        mainConfiguration = provider.get();

        if (mainConfiguration.isBackUp()) {
            display = new BackUpDisplay();
        } else {
            display = new NewLayout();
            GraphicUtil.showOnScreen(mainConfiguration.monitor(), (JFrame) display);
        }
    }


    private static void printConfiguration() {
        logger.info("Konfiguration:" +
                ", Projekt-Ordner: " + systemInformation.getProjectDirectory().getAbsolutePath() +
                ", Log-Ordner:" + systemInformation.getLoggingFolder().getAbsolutePath() +

                ", Working-Ordner:" + systemInformation.getWorkingFolder().getAbsolutePath() + "" +
                ", Config-Ordner:" + systemInformation.getConfigFolder().getAbsolutePath());
    }


    static void printAlarmMonitor() {
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



