package de.alarm_monitor.visual;

import de.alarm_monitor.util.LayoutCalculator;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HorizontalIconPane extends JPanel  {


    private static final String PATH_11_1 = "res/11_1.jpg";
    private static final String PATH_65_1 = "res/65_1.jpg";
    private static final String PATH_40_1 = "res/40_1.jpg"; //HLF
    private static final String PATH_20_1 = "res/20_1.jpg";
    private static final String PATH_31_1 = "res/31_1.jpg";

    List<VehiclePanel> vehiclePanelList = new ArrayList<>();

    private final VehiclePanel panel11_1;
    private final VehiclePanel panel65_1;
    private final VehiclePanel panel40_1;
    private final VehiclePanel panel20_1;
    private final VehiclePanel panel31_1;

    public static final Color ALARM_COLOR = Color.green;


    public HorizontalIconPane(final Rectangle rectangle) {


        this.setLayout(null);
        final LayoutCalculator calculator = new LayoutCalculator(rectangle.width, rectangle.height, 5, 1);


        Rectangle rectangle11_1 = calculator.getRectangleForPosition(0, 0, 1, 1);
        panel11_1 = new VehiclePanel(rectangle11_1, PATH_11_1, "Gangkofen 11/1");
        panel11_1.setBounds(rectangle11_1);
        this.add(panel11_1);
        vehiclePanelList.add(panel11_1);

        Rectangle rectangle65_1 =  calculator.getRectangleForPosition(1,0, 1, 1);
        panel65_1= new VehiclePanel(rectangle65_1,PATH_65_1, "Gangkofen 65/1" );
        panel65_1.setBounds(rectangle65_1);
        this.add(panel65_1);
        vehiclePanelList.add(panel65_1);

        Rectangle rectangle40_1 = calculator.getRectangleForPosition(2, 0, 1, 1);
        panel40_1= new VehiclePanel(rectangle40_1, PATH_40_1, " Gangkofen 40/1");
        panel40_1.setBounds(rectangle40_1);
        this.add(panel40_1);
        vehiclePanelList.add(panel40_1);

        Rectangle rectangle20_1 = calculator.getRectangleForPosition(3, 0, 1, 1);
        panel20_1= new VehiclePanel(rectangle20_1, PATH_20_1, "Gangkofen 20/1");
        panel20_1.setBounds(rectangle20_1);
        this.add(panel20_1);
        vehiclePanelList.add(panel20_1);

        Rectangle rectangle31_1 = calculator.getRectangleForPosition(4, 0, 1, 1);
        panel31_1= new VehiclePanel(rectangle31_1, PATH_31_1, "Gangkofen 31/1");
        panel31_1.setBounds(rectangle31_1);
        this.add(panel31_1);
        vehiclePanelList.add(panel31_1);



        this.setBackground(Color.BLACK);

        this.setVisible(true);
    }

    public void highlightRequestedResources(String resources){
        for (VehiclePanel vehiclePanel : vehiclePanelList) {
            vehiclePanel.highlight(resources);
        }
    }

    public void unHighlightRequestedResources(){
        for (VehiclePanel vehiclePanel : vehiclePanelList) {
            vehiclePanel.unHighlight();
        }

    }

    private static class VehiclePanel extends JPanel{




        private final String matchingRessource;
        private final int verticalBroder;
        private final int horizontalBroder;

        public void highlight(String allRessources){
            if(allRessources.contains(matchingRessource)){
                setBackground(ALARM_COLOR);
            }

        }

        public void unHighlight(){
            setBackground(Color.WHITE);
        }

        VehiclePanel(Rectangle rectangle, String pathImage, String matchingRessource){
            this.matchingRessource = matchingRessource;
            this.setBorder(new BorderUIResource.LineBorderUIResource(Color.BLACK));
            this.setBackground(Color.white);
            verticalBroder= rectangle.height/20;
            horizontalBroder= rectangle.width/20;
            JLabel label = new JLabel();
            Rectangle imageRect = new Rectangle(rectangle.x + horizontalBroder, rectangle.y + verticalBroder, rectangle.width - 2*horizontalBroder, rectangle.height - 2* verticalBroder);
            label.setBounds(imageRect);
            label.setIcon(getImageIcon(imageRect, pathImage));
            this.add(label);
        }

        private static ImageIcon getImageIcon(Rectangle rectangle11_1, String path) {
            ImageIcon imageIcon = new ImageIcon(path);
            return new ImageIcon(imageIcon.getImage().getScaledInstance(rectangle11_1.width, rectangle11_1.height, Image.SCALE_SMOOTH));
        }




    }


}
