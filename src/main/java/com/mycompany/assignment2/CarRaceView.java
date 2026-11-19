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
        Group group = new Group();

        switch (car.getType()) {
            case "Sports Coupe":
                addBodyParts(group, color,
                        new double[]{0, 34, 0, 25, 8, 22, 92, 22, 104, 26, 110, 29, 110, 34},
                        new double[]{30, 22, 52, 9, 76, 9, 98, 22},
                        new double[][]{{50, 12, 10, 7}, {62, 12, 10, 7}});
                group.getChildren().addAll(
                        createRect(0, 15, 14, 3, color.darker().darker()),
                        createRect(3, 18, 3, 7, color.darker().darker()));
                addPlate(group, car, 23, 10, 11);
                break;
            case "Hatchback":
                addBodyParts(group, color,
                        new double[]{0, 34, 0, 20, 6, 18, 92, 18, 106, 24, 110, 28, 110, 34},
                        new double[]{4, 18, 8, 3, 66, 3, 86, 18},
                        new double[][]{{16, 6, 18, 9}, {38, 6, 18, 9}});
                addPlate(group, car, 21, 12, 12);
                break;
            case "Taxi":
                addBodyParts(group, color,
                        new double[]{0, 34, 0, 20, 6, 18, 94, 18, 110, 24, 110, 34},
                        new double[]{20, 18, 28, 4, 76, 4, 88, 18},
                        new double[][]{{32, 7, 16, 9}, {54, 7, 16, 9}});
                Rectangle sign = createRect(40, -2, 24, 6, Color.web("#212121"));
                Text signText = new Text(43, 3, "TAXI");
                signText.setFill(Color.WHITE);
                signText.setFont(Font.font("Arial", FontWeight.BOLD, 6));
                group.getChildren().addAll(sign, signText);
                for (int i = 0; i < 22; i += 2) {
                    group.getChildren().add(createRect(i * 5, 29, 5, 3, Color.BLACK));
                }
                addPlate(group, car, 21, 12, 12);
                break;
            default:
                addBodyParts(group, color,
                        new double[]{0, 34, 0, 21, 8, 18, 92, 18, 106, 22, 110, 26, 110, 34},
                        new double[]{22, 18, 32, 4, 70, 4, 84, 18},
                        new double[][]{{36, 7, 14, 9}, {54, 7, 14, 9}});
                addPlate(group, car, 21, 12, 12);
                break;
        }

        List<Group> wheels = new ArrayList<>();
        wheels.add(createWheel(24, 36));
        wheels.add(createWheel(86, 36));
        wheelNodes.put(car, wheels);
        group.getChildren().addAll(wheels);
        return group;
    }
    
    private void addBodyParts(Group group, Color color, double[] bodyPoints, double[] roofPoints, double[][] windows) {
        Polygon body = new Polygon(bodyPoints);
        body.setFill(color);
        Polygon roof = new Polygon(roofPoints);
        roof.setFill(color.darker());
        group.getChildren().addAll(body, roof);
        for (double[] w : windows) {
            group.getChildren().add(createRect(w[0], w[1], w[2], w[3], Color.web("#81d4fa")));
        }
    }

    private void addPlate(Group group, CarRaceModel.Car car, double y, double height, double fontSize) {
        Rectangle plate = createRect(42, y, 26, height, Color.WHITE);
        plate.setArcWidth(4);
        plate.setArcHeight(4);
        Text number = new Text(47, y + height - 2, String.valueOf(car.getNumber()));
        number.setFill(Color.BLACK);
        number.setFont(Font.font("Arial", FontWeight.BOLD, fontSize));
        group.getChildren().addAll(plate, number);
    }

    private Rectangle createRect(double x, double y, double width, double height, Color color) {
        Rectangle rect = new Rectangle(x, y, width, height);
        rect.setFill(color);
        return rect;
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
