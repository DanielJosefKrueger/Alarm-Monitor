package de.alarm_monitor.watcher;

import java.io.OutputStream;
import java.net.Socket;

import static de.alarm_monitor.exception.ExceptionUtil.logException;

class SocketProcessor extends Thread {


    private final Socket socket;

    SocketProcessor(final Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (OutputStream out = socket.getOutputStream()) {
            out.write(1);
            out.flush();
        } catch (final Exception e) {
            logException(this.getClass(), "Fehler beim Antworten der Anfrage des  Watchers", e);
        }
    }
}
