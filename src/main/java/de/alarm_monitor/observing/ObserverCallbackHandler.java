package de.alarm_monitor.observing;

import de.alarm_monitor.callback.NewPdfCallback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class ObserverCallbackHandler extends Thread {

    private NewPdfCallback callback;
    private File file;
    private static final Logger logger = LogManager.getLogger(ObserverCallbackHandler.class);

    ObserverCallbackHandler(NewPdfCallback callback, File file) {
        this.callback = callback;
        this.file = file;
    }

    @Override
    public void run() {
        try {
            callback.onNewPdfFile(file);
        } catch (Exception e) {
            logger.error("Fehler beim Verarbeiten des Pdf", e);
        }
    }
}
