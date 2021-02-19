package de.alarm_monitor.visual.fullscreen;

import de.alarm_monitor.util.GraphicUtil;

import javax.swing.*;
import java.awt.*;

class VerticalVehiclePanel extends JPanel {

    private static final Color ALARM_COLOR = Color.red;
    private static final Color NO_ALARM_COLOR = Color.green;
    private static final Font FONT = new Font(Font.SANS_SERIF, Font.BOLD, 30);


    private final Icon icon;
    private final String name;

    private final String matchingRessource;
    private final int verticalBroder;
    private final int horizontalBroder;
    private final int gapBetweenIconAndLabel;
    private final double percentageGapBetweenIconAndText = 0.05;
    private final double percentageIcon=0.40;
    private final double PERCENTAGE_HORIZONTAL_BORDER = 0.00;
    private final double PERCENTAGE_VERTICAL_BORDER = 0.00;

    private final JLabel nameLabel;
    private final JLabel iconLabel;

    void highlight(String allRessources){
        if(allRessources.contains(matchingRessource)){
            this.nameLabel.setBackground(ALARM_COLOR);
        }else{
            this.iconLabel.setIcon(null);
            this.nameLabel.setText("");
        }

    }

    void unHighlight(){
        this.iconLabel.setIcon(this.icon);
        this.nameLabel.setBackground(NO_ALARM_COLOR);
        this.nameLabel.setText(this.name);
    }

    VerticalVehiclePanel(Rectangle rectangle, String pathImage, String matchingRessource, String name){

        this.name = name;
        this.matchingRessource = matchingRessource;
        this.setBackground(Color.WHITE);
        verticalBroder= (int)(rectangle.height*PERCENTAGE_HORIZONTAL_BORDER);
        horizontalBroder= (int)(rectangle.width*PERCENTAGE_VERTICAL_BORDER);
        int widthWithoutPadding = rectangle.width - 2*horizontalBroder;
        int heightWithoutPadding = rectangle.height - 2* verticalBroder;
        gapBetweenIconAndLabel = (int)(widthWithoutPadding * percentageGapBetweenIconAndText);

        int widthIcon = (int)(widthWithoutPadding * percentageIcon);
        int xIcon = rectangle.x+horizontalBroder;
        int yIcon = rectangle.y + verticalBroder;

        int xLabel = xIcon + widthIcon + gapBetweenIconAndLabel;
        int yLabel = rectangle.y +verticalBroder+  heightWithoutPadding/5;
        int heightLabel = heightWithoutPadding - 2*heightWithoutPadding/5;
        int widthLabel = widthWithoutPadding- horizontalBroder - xLabel;

        nameLabel = new JLabel(name);
        nameLabel.setBounds(xLabel, yLabel, widthLabel, heightLabel);
        nameLabel.setOpaque(true);
        nameLabel.setBackground(Color.GREEN);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(FONT);
        this.setOpaque(true);


        iconLabel = new JLabel();
        Rectangle imageRect = new Rectangle(xIcon, yIcon, widthIcon , heightWithoutPadding);
        iconLabel.setBounds(imageRect);
        icon = GraphicUtil.getImageIcon(imageRect, pathImage);
        iconLabel.setIcon(icon);
        this.add(iconLabel);
        this.add(nameLabel);
        this.setVisible(true);
    }


}