package de.alarm_monitor.watcher;

import com.google.inject.Inject;
import com.google.inject.Provider;
import de.alarm_monitor.configuration.MainConfiguration;

import java.net.ServerSocket;
import java.net.Socket;

import static de.alarm_monitor.exception.ExceptionUtil.logException;

public class ServerSocketHandler extends Thread {

    private final MainConfiguration mainConfiguration;

    @Inject
    public ServerSocketHandler(final Provider<MainConfiguration> provider) {
        this.mainConfiguration = provider.get();
    }

    @Override
    public void run() {
        try {
            openSocket();
        } catch (final Exception e) {
            logException(this.getClass(), "Fehler beim Öffnen des Ports für den Watcher", e);
        }
    }

    private void openSocket() {
        try {
            final ServerSocket serverSocket = new ServerSocket(mainConfiguration.getPort());
            while (true) {
                final Socket socket = serverSocket.accept();
                final SocketProcessor processor = new SocketProcessor(socket);
                processor.start();
            }
        } catch (final Exception e) {
            logException(this.getClass(), "Fehler beim Öffnen des Ports für den Watcher", e);
        }
    }
}
