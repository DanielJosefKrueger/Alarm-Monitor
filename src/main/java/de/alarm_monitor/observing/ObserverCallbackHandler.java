package de.alarm_monitor.observing;

import de.alarm_monitor.callback.NewPdfCallback;

import java.io.File;

import static de.alarm_monitor.exception.ExceptionUtil.logException;

class ObserverCallbackHandler extends Thread {

    private final NewPdfCallback callback;
    private final File file;

    ObserverCallbackHandler(final NewPdfCallback callback, final File file) {
        this.callback = callback;
        this.file = file;
    }

    @Override
    public void run() {
        try {
            callback.onNewPdfFile(file);
        } catch (final Exception e) {
            logException(this.getClass(), "Fehler beim Verarbeiten des Pdf", e);
        }
    }
}
