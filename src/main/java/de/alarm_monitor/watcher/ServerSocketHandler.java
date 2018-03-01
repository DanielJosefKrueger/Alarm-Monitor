package de.alarm_monitor.watcher;

import com.google.inject.Inject;
import com.google.inject.Provider;
import de.alarm_monitor.configuration.MainConfiguration;

import java.net.ServerSocket;
import java.net.Socket;

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void openSocket() {
        try {
            ServerSocket serverSocket = new ServerSocket(mainConfiguration.getPort());
            while (true) {
                Socket socket = serverSocket.accept();
                SocketProcessor processor = new SocketProcessor(socket);
                processor.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
