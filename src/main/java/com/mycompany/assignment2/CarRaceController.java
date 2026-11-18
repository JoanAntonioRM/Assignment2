/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Joan
 */
public class CarRaceController {
    
    @FXML
    private Label captionLabel;
    @FXML
    private Button skipButton;
    @FXML
    private ImageView slideImage;

    @FXML
    private Pane trackPane;
    @FXML
    private Button startButton;
    @FXML
    private Button pauseButton;
    @FXML
    private Button resetButton;
    @FXML
    private Slider speedSlider;
    @FXML
    private Label speedValueLabel;
    @FXML
    private Label messageTitleLabel;
    @FXML
    private Label messageLabel;
    @FXML
    private VBox resultsBox;
    @FXML
    private Label infoLabel;

    @FXML
    void onSkip(ActionEvent event) {
    }
    
    @FXML
    private void onStart(ActionEvent event) {
    }

    @FXML
    private void onPause(ActionEvent event) {
    }

    @FXML
    private void onReset(ActionEvent event) {
    }

    
}
