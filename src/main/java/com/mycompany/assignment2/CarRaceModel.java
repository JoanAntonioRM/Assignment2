/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Joan
 */
public class CarRaceModel {
    public static final double TRACK_WIDTH = 800;
    public static final double LANE_HEIGHT = 85;
    public static final double CAR_WIDTH = 110;
    public static final double CAR_HEIGHT = 44;
    public static final double START_X = 15;
    public static final double FINISH_X = 780;

    private static final double MIN_SPEED = 60;
    private static final double MAX_SPEED = 90;
    
    private final List<Car> cars = new ArrayList<>();
    private final List<Car> finishOrder = new ArrayList<>();
    private final Random random = new Random();
    
    public static class Car {
        
        private final String type;
        private final int number;
        private final String colorName;
        private final String colorHex;
        private double x;
        private double y;
        private double speed;
        private int finishPlace;
        
        public Car(String type, int number, String colorName, String colorHex, double x, double y) {
            this.type = type;
            this.number = number;
            this.colorName = colorName;
            this.colorHex = colorHex;
            this.x = x;
            this.y = y;
        }
        
        public String getType() {
            return type;
        }

        public int getNumber() {
            return number;
        }

        public String getColorName() {
            return colorName;
        }

        public String getColorHex() {
            return colorHex;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getSpeed() {
            return speed;
        }

        public int getFinishPlace() {
            return finishPlace;
        }
        
        public void updatePosition(double newX, double newY) {
            this.x = newX;
            this.y = newY;
        }

        public void setSpeed(double speed) {
            this.speed = speed;
        }

        public void setFinishPlace(int finishPlace) {
            this.finishPlace = finishPlace;
        }

        public String getLabel() {
            return "Car #" + number + " (" + colorName + ")";
        }
    }
    
    public CarRaceModel() {
        addCar("Sedan", 23, "Blue", "#1e88e5");
        addCar("Sports Coupe", 45, "Red", "#e53935");
        addCar("Hatchback", 78, "Green", "#43a047");
        addCar("Taxi", 88, "Yellow", "#fdd835");
        randomizeSpeeds();
    }
    
    private void addCar(String type, int number, String colorName, String colorHex) {
        double y = cars.size() * LANE_HEIGHT + (LANE_HEIGHT - CAR_HEIGHT) / 2;
        cars.add(new Car(type, number, colorName, colorHex, START_X, y));
    }
    
    public List<Car> getCars() {
        return cars;
    }

    public List<Car> getFinishOrder() {
        return finishOrder;
    }

    public double getTravelDistance() {
        return FINISH_X - (START_X + CAR_WIDTH);
    }

    public void randomizeSpeeds() {
        for (Car car : cars) {
            car.setSpeed(MIN_SPEED + random.nextDouble() * (MAX_SPEED - MIN_SPEED));
        }
    }
    
}
