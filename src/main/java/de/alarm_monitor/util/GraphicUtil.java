package de.alarm_monitor.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;

import static de.alarm_monitor.exception.ExceptionUtil.logException;

public class GraphicUtil {
    private static final Logger logger = LogManager.getLogger(GraphicUtil.class);

    public static void showOnScreen(final int screen, final JFrame frame) {
        try {
            final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            final GraphicsDevice[] gd = ge.getScreenDevices();
            if (screen > -1 && screen < gd.length) {
                frame.setLocation(gd[screen].getDefaultConfiguration().getBounds().x, frame.getY());
            } else if (gd.length > 0) {
                frame.setLocation(gd[0].getDefaultConfiguration().getBounds().x, frame.getY());
            } else {
                logger.warn("No Screens found");
            }
        } catch (final Exception e) {
            logException(GraphicUtil.class, "Fehler beim Justieren des Monitors", e);
        }
    }
}
