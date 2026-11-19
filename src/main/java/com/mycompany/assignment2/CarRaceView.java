/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author Joan
 */
public class CarRaceView {
    private final Map<CarRaceModel.Car, Group> carNodes = new HashMap<>();
    private final Map<CarRaceModel.Car, List<Group>> wheelNodes = new HashMap<>();
    
    public void addCars(Pane track, List<CarRaceModel.Car> cars) {
        for (CarRaceModel.Car car : cars) {
            Group node = createCar(car);
            node.setLayoutX(CarRaceModel.START_X);
            node.setLayoutY(car.getY());
            track.getChildren().add(node);
            carNodes.put(car, node);
        }
    }
    
    private Group createCar(CarRaceModel.Car car) {
        Color color = Color.web(car.getColorHex());

        Polygon body = new Polygon(0, 34, 0, 22, 14, 18, 96, 18, 110, 24, 110, 34);
        body.setFill(color);

        Polygon roof = new Polygon(24, 18, 36, 3, 72, 3, 88, 18);
        roof.setFill(color.darker());

        Rectangle window1 = new Rectangle(40, 6, 14, 10);
        Rectangle window2 = new Rectangle(58, 6, 14, 10);
        window1.setFill(Color.web("#81d4fa"));
        window2.setFill(Color.web("#81d4fa"));

        Rectangle plate = new Rectangle(42, 21, 26, 12);
        plate.setArcWidth(4);
        plate.setArcHeight(4);
        plate.setFill(Color.WHITE);

        Text number = new Text(47, 31, String.valueOf(car.getNumber()));
        number.setFill(Color.BLACK);
        number.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        List<Group> wheels = new ArrayList<>();
        wheels.add(createWheel(24, 36));
        wheels.add(createWheel(86, 36));
        wheelNodes.put(car, wheels);

        Group group = new Group(body, roof, window1, window2, plate, number);
        group.getChildren().addAll(wheels);
        return group;
    }
    
    private Group createWheel(double centerX, double centerY) {
        Circle tire = new Circle(centerX, centerY, 8);
        tire.setFill(Color.web("#111111"));
        Circle hub = new Circle(centerX, centerY, 5);
        hub.setFill(Color.web("#cfd8dc"));
        Line spoke = new Line(centerX, centerY, centerX + 5, centerY);
        spoke.setStroke(Color.web("#263238"));
        spoke.setStrokeWidth(2);
        return new Group(tire, hub, spoke);
    }
    
    public Group getCarNode(CarRaceModel.Car car) {
        return carNodes.get(car);
    }

    public List<Group> getWheels(CarRaceModel.Car car) {
        return wheelNodes.get(car);
    }

    public Image loadImage(CarRaceModel.Car car) {
        java.net.URL url = getClass().getResource("images/car" + car.getNumber() + ".png");
        return url == null ? null : new Image(url.toExternalForm());
    }

    public List<Node> createResultRows(List<CarRaceModel.Car> order) {
        List<Node> rows = new ArrayList<>();
        Label header = new Label("Final Results:");
        header.setStyle("-fx-font-weight: bold;");
        rows.add(header);
        for (CarRaceModel.Car car : order) {
            Circle dot = new Circle(6, Color.web(car.getColorHex()));
            Label text = new Label(ordinal(car.getFinishPlace()) + "  " + car.getLabel());
            HBox row = new HBox(8, dot, text);
            row.setAlignment(Pos.CENTER_LEFT);
            rows.add(row);
        }
        return rows;
    }

    private String ordinal(int place) {
        switch (place) {
            case 1:
                return "1st";
            case 2:
                return "2nd";
            case 3:
                return "3rd";
            default:
                return place + "th";
        }
    }
}
