package de.alarm_monitor.visual;

import de.alarm_monitor.util.LayoutCalculator;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VerticalIconPane extends JPanel  {


    private static final String PATH_11_1 = "res/11_1.jpg";
    private static final String PATH_65_1 = "res/65_1.jpg";
    private static final String PATH_40_1 = "res/40_1.jpg"; //HLF
    private static final String PATH_20_1 = "res/20_1.jpg";
    private static final String PATH_31_1 = "res/31_1.jpg";

    List<VerticalVehiclePanel> verticalVehiclePanelList = new ArrayList<>();

    private final VerticalVehiclePanel panel11_1;
    private final VerticalVehiclePanel panel65_1;
    private final VerticalVehiclePanel panel40_1;
    private final VerticalVehiclePanel panel20_1;
    private final VerticalVehiclePanel panel31_1;




    public VerticalIconPane(final Rectangle rectangle) {


        this.setLayout(null);
        final LayoutCalculator calculator = new LayoutCalculator(rectangle.width, rectangle.height, 1, 5);


        Rectangle rectangle11_1 = calculator.getRectangleForPosition(0, 0, 1, 1);
        panel11_1 = new VerticalVehiclePanel(rectangle11_1, PATH_11_1, "Gangkofen 11/1");
        panel11_1.setBounds(rectangle11_1);
        this.add(panel11_1);
        verticalVehiclePanelList.add(panel11_1);


        Rectangle rectangle65_1 =  calculator.getRectangleForPosition(0,1, 1, 1);
        panel65_1= new VerticalVehiclePanel(rectangle65_1,PATH_65_1, "Gangkofen 65/1" );
        panel65_1.setBounds(rectangle65_1);
        this.add(panel65_1);
        verticalVehiclePanelList.add(panel65_1);

        Rectangle rectangle40_1 = calculator.getRectangleForPosition(0, 2, 1, 1);
        panel40_1= new VerticalVehiclePanel(rectangle40_1, PATH_40_1, " Gangkofen 40/1");
        panel40_1.setBounds(rectangle40_1);
        this.add(panel40_1);
        verticalVehiclePanelList.add(panel40_1);

        Rectangle rectangle20_1 = calculator.getRectangleForPosition(0, 3, 1, 1);
        panel20_1= new VerticalVehiclePanel(rectangle20_1, PATH_20_1, "Gangkofen 20/1");
        panel20_1.setBounds(rectangle20_1);
        this.add(panel20_1);
        verticalVehiclePanelList.add(panel20_1);

        Rectangle rectangle31_1 = calculator.getRectangleForPosition(0, 4, 1, 1);
        panel31_1= new VerticalVehiclePanel(rectangle31_1, PATH_31_1, "Gangkofen 31/1");
        panel31_1.setBounds(rectangle31_1);
        this.add(panel31_1);
        verticalVehiclePanelList.add(panel31_1);



        this.setBackground(Color.BLACK);

        this.setVisible(true);
    }

    public void highlightRequestedResources(String resources){
        for (VerticalVehiclePanel verticalVehiclePanel : verticalVehiclePanelList) {
            verticalVehiclePanel.highlight(resources);
        }
    }

    public void unHighlightRequestedResources(){
        for (VerticalVehiclePanel verticalVehiclePanel : verticalVehiclePanelList) {
            verticalVehiclePanel.unHighlight();
        }

    }

    private static class VerticalVehiclePanel extends JPanel{

        public static final Color ALARM_COLOR = Color.red;
        public static final Color NO_ALARM_COLOR = Color.green;
        private static final Font FONT = new Font(Font.SANS_SERIF, Font.BOLD, 20);



        private final String matchingRessource;
        private final int verticalBroder;
        private final int horizontalBroder;
        private final int gapBetweenIconAndLabel;
        private final double percentageGapBetweenIconAndText = 0.05;
        private final double percentageIcon=0.30;
        private final double PERCENTAGE_HORIZONTAL_BORDER = 0.10;
        private final double PERCENTAGE_VERTICAL_BORDER = 0.10;

        private final JLabel nameLabel;
        private final JLabel iconLabel;

        public void highlight(String allRessources){
            if(allRessources.contains(matchingRessource)){
                this.nameLabel.setBackground(ALARM_COLOR);
            }

        }

        public void unHighlight(){
            this.nameLabel.setBackground(NO_ALARM_COLOR);
        }

        VerticalVehiclePanel(Rectangle rectangle, String pathImage, String matchingRessource){

            this.setLayout(null);
            this.matchingRessource = matchingRessource;
            this.setBackground(Color.blue);
            verticalBroder= rectangle.height/10;
            horizontalBroder= rectangle.width/10;
            int widthWithoutPadding = rectangle.width - 2*horizontalBroder;
            int heightWithoutPadding = rectangle.height - 2* verticalBroder;
            gapBetweenIconAndLabel = (int)(widthWithoutPadding * percentageGapBetweenIconAndText);

            int widthIcon = (int)(widthWithoutPadding * percentageIcon);
            int xIcon = rectangle.x+horizontalBroder;
            int yIcon = rectangle.y + verticalBroder;
            int heightIcon = heightWithoutPadding;

            int xLabel = rectangle.x + xIcon + widthIcon + gapBetweenIconAndLabel;
            int yLabel =rectangle.y +verticalBroder+  heightWithoutPadding/5;
            int heightLabel = heightWithoutPadding - 2*heightWithoutPadding/5;
            int widthLabel = widthWithoutPadding- horizontalBroder - xLabel;

            nameLabel = new JLabel(matchingRessource);
            nameLabel.setBounds(xLabel, yLabel, widthLabel, heightLabel);
            nameLabel.setOpaque(true);
            nameLabel.setBackground(Color.GREEN);
            nameLabel.setForeground(Color.WHITE);
            nameLabel.setFont(FONT);
            this.setOpaque(true);
            this.add(nameLabel);


            iconLabel = new JLabel();
            Rectangle imageRect = new Rectangle(xIcon, yIcon, widthIcon , heightIcon);
            iconLabel.setBounds(imageRect);
            iconLabel.setIcon(getImageIcon(imageRect, pathImage));
            this.add(iconLabel);



        }

        private static ImageIcon getImageIcon(Rectangle rectangle11_1, String path) {
            ImageIcon imageIcon = new ImageIcon(path);
            return new ImageIcon(imageIcon.getImage().getScaledInstance(rectangle11_1.width, rectangle11_1.height, Image.SCALE_SMOOTH));
        }




    }


}
