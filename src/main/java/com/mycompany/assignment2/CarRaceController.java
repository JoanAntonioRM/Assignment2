/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment2;

import com.mycompany.assignment2.CarRaceModel.Car;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 *
 * @author Joan
 */
public class CarRaceController {
    
    private static final double SLIDE_SECONDS = 3;
    
    private enum State { READY, RUNNING, PAUSED, FINISHED }
    
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
    
    private final CarRaceModel model = new CarRaceModel();
    private final CarRaceView view = new CarRaceView();

    private State state = State.READY;
    private Timeline slideshow;
    private MediaPlayer player;
    private boolean raceShown = false;
    
    public void startSlideshow() {
        playSound();
        List<Car> cars = model.getCars();
        showSlide(0);
        slideshow = new Timeline();
        for (int i = 1; i < cars.size(); i++) {
            final int index = i;
            slideshow.getKeyFrames().add(new KeyFrame(Duration.seconds(i * SLIDE_SECONDS), e -> showSlide(index)));
        }
        slideshow.getKeyFrames().add(new KeyFrame(Duration.seconds(cars.size() * SLIDE_SECONDS), e -> endSlideshow()));
        slideshow.play();
    }

    private void showSlide(int index) {
        Car car = model.getCars().get(index);
        Image image = view.loadImage(car);
        if (image == null) {
            System.err.println("Missing image for car " + car.getNumber());
            return;
        }
        slideImage.setImage(image);
        captionLabel.setText("Car #" + car.getNumber() + "  -  " + car.getColorName() + " " + car.getType());
        javafx.animation.FadeTransition fade = new javafx.animation.FadeTransition(Duration.millis(600), slideImage);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    @FXML
    private void onSkip() {
        endSlideshow();
    }

    private void endSlideshow() {
        if (raceShown) {
            return;
        }
        raceShown = true;
        if (slideshow != null) {
            slideshow.stop();
        }
        if (player != null) {
            player.stop();
        }
        try {
            App.showRace();
        } catch (IOException e) {
            System.err.println("Could not load secondary.fxml: " + e.getMessage());
        }
    }

    private void playSound() {
        try {
            URL url = getClass().getResource("sounds/welcome.wav");
            if (url != null) {
                player = new MediaPlayer(new Media(url.toExternalForm()));
                player.setVolume(0.6);
                player.setOnError(() -> System.err.println("Sound error: " + player.getError()));
                player.play();
            }
        } catch (Exception e) {
            System.err.println("Could not play sound: " + e.getMessage());
        }
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
