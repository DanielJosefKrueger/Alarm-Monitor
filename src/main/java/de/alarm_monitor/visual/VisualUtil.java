package de.alarm_monitor.visual;

import javax.swing.*;
import java.awt.*;

public class VisualUtil {

    public static ImageIcon getImageIcon(Rectangle rectangle, String path) {
        ImageIcon imageIcon = new ImageIcon(path);
        return new ImageIcon(imageIcon.getImage().getScaledInstance(rectangle.width, rectangle.height, Image.SCALE_SMOOTH));
    }
}
