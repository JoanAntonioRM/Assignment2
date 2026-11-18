/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment2;

/**
 *
 * @author Joan
 */
public class CarRaceModel {
    
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
    }
    
}
