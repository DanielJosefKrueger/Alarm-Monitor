package de.alarm_monitor.visual.halfscreen;

import de.alarm_monitor.util.LayoutCalculator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HorizontaIconPane extends JPanel  {


    private static final String PATH_11_1 = "res/11_1.jpg";
    private static final String PATH_65_1 = "res/65_1.jpg";
    private static final String PATH_40_1 = "res/40_1.jpg"; //HLF
    private static final String PATH_20_1 = "res/20_1.jpg";
    private static final String PATH_31_1 = "res/31_1.jpg";

    List<HorizontalVehiclePanel> verticalVehiclePanelList = new ArrayList<>();

    private final HorizontalVehiclePanel panel11_1;
    private final HorizontalVehiclePanel panel65_1;
    private final HorizontalVehiclePanel panel40_1;
    private final HorizontalVehiclePanel panel20_1;
    private final HorizontalVehiclePanel panel31_1;


    public HorizontaIconPane(final Rectangle rectangle) {
        this.setLayout(null);
        final LayoutCalculator calculator = new LayoutCalculator(rectangle.width, rectangle.height, 50, 10);
        this.setBackground(Color.WHITE);
        this.setOpaque(true);

        Rectangle rectangle11_1 = calculator.getRectangleForPosition(0, 0, 10, 10);
        panel11_1 = new HorizontalVehiclePanel(rectangle11_1, PATH_11_1, "Gangkofen 11", "Gangkofen 11/1");
        panel11_1.setBounds(rectangle11_1);
        this.add(panel11_1);
        verticalVehiclePanelList.add(panel11_1);


        Rectangle rectangle65_1 =  calculator.getRectangleForPosition(10,0, 10, 10);
        panel65_1= new HorizontalVehiclePanel(rectangle65_1,PATH_65_1, "Gangkofen 65", "Gangkofen 65/1" );
        panel65_1.setBounds(rectangle65_1);
        this.add(panel65_1);
        verticalVehiclePanelList.add(panel65_1);

        Rectangle rectangle40_1 = calculator.getRectangleForPosition(20, 0, 10, 10);
        panel40_1= new HorizontalVehiclePanel(rectangle40_1, PATH_40_1, "Gangkofen 40", "Gangkofen 40/1");
        panel40_1.setBounds(rectangle40_1);
        this.add(panel40_1);
        verticalVehiclePanelList.add(panel40_1);

        Rectangle rectangle20_1 = calculator.getRectangleForPosition(30, 0, 10, 10);
        panel20_1= new HorizontalVehiclePanel(rectangle20_1, PATH_20_1, "Gangkofen 20","Gangkofen 20/1" );
        panel20_1.setBounds(rectangle20_1);
        this.add(panel20_1);
        verticalVehiclePanelList.add(panel20_1);

        Rectangle rectangle31_1 = calculator.getRectangleForPosition(40, 0, 10, 10);
        panel31_1= new HorizontalVehiclePanel(rectangle31_1, PATH_31_1, "Gangkofen 31","Gangkofen 31/1");
        panel31_1.setBounds(rectangle31_1);
        this.add(panel31_1);
        verticalVehiclePanelList.add(panel31_1);

        this.setVisible(true);
    }

    public void highlightRequestedResources(String resources){
        for (HorizontalVehiclePanel vehiclePanel : verticalVehiclePanelList) {
            vehiclePanel.highlight(resources);
        }
    }

    public void unHighlightRequestedResources(){
        for (HorizontalVehiclePanel verticalVehiclePanel : verticalVehiclePanelList) {
            verticalVehiclePanel.unHighlight();
        }

    }
}
