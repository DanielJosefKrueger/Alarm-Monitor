package de.alarm_monitor.watcher;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class SocketProcessor extends Thread {


    private final Socket socket;

    SocketProcessor(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (OutputStream out = socket.getOutputStream()) {
            out.write(1);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
