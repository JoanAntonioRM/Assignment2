/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment2;

import com.mycompany.assignment2.CarRaceModel.Car;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Group;
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
    private static final double WHEEL_TURN_SECONDS = 0.5;
    
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
    private final List<TranslateTransition> moves = new ArrayList<>();
    private final List<RotateTransition> spins = new ArrayList<>();

    private State state = State.READY;
    private Timeline slideshow;
    private MediaPlayer player;
    private boolean raceShown = false;
    
    @FXML
    private void initialize() {
        if (trackPane != null) {
            setupRace();
        }
    }
    
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
            App.setRoot("secondary");
        }catch (IOException e) {
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
    
    private void setupRace() {
        view.addCars(trackPane, model.getCars());
        for (Car car : model.getCars()) {
            view.getCarNode(car).translateXProperty().addListener((obs, oldValue, newValue) -> model.updateCarPosition(car, CarRaceModel.START_X + newValue.doubleValue()));
        }
        speedSlider.valueProperty().addListener((obs, oldValue, newValue) -> changeSpeed(newValue.doubleValue()));
        updateButtons();
    }

    @FXML
    private void onStart() {
        if (state == State.READY) {
            createAnimations();
        }
        if (state == State.READY || state == State.PAUSED) {
            showMessage("Race in progress...", "The cars are on their way to the finish line!");
            moves.forEach(Animation::play);   // play() also resumes a paused transition
            spins.forEach(Animation::play);
            state = State.RUNNING;
            updateButtons();
        }
    }

    @FXML
    private void onPause() {
        if (state == State.RUNNING) {
            moves.forEach(Animation::pause);
            spins.forEach(Animation::pause);
            state = State.PAUSED;
            showMessage("Race paused", "Press Start to continue.");
            updateButtons();
        }
    }

    @FXML
    private void onReset() {
        moves.forEach(Animation::stop);
        spins.forEach(Animation::stop);
        moves.clear();
        spins.clear();
        for (Car car : model.getCars()) {
            view.getCarNode(car).setTranslateX(0);
            view.getWheels(car).forEach(wheel -> wheel.setRotate(0));
        }
        model.reset();
        state = State.READY;
        showMessage("Ready to race!", "Press Start to begin the race.");
        updateButtons();
    }
    
    private void createAnimations() {
        double distance = model.getTravelDistance();
        double rate = speedSlider.getValue();
        for (Car car : model.getCars()) {
            TranslateTransition move = new TranslateTransition(Duration.seconds(distance / car.getSpeed()), view.getCarNode(car));
            move.setFromX(0);
            move.setToX(distance);
            move.setInterpolator(Interpolator.LINEAR);
            move.setRate(rate);

            List<RotateTransition> carSpins = new ArrayList<>();
            for (Group wheel : view.getWheels(car)) {
                RotateTransition spin = new RotateTransition(Duration.seconds(WHEEL_TURN_SECONDS), wheel);
                spin.setByAngle(360);
                spin.setCycleCount(Animation.INDEFINITE);
                spin.setInterpolator(Interpolator.LINEAR);
                spin.setRate(rate);
                carSpins.add(spin);
            }
            move.setOnFinished(e -> {
                carSpins.forEach(Animation::stop);
                onCarFinished(car);
            });
            moves.add(move);
            spins.addAll(carSpins);
        }
    }

    private void onCarFinished(Car car) {
        int place = model.registerFinish(car);
        if (place == 1) {
            showMessage("We have a winner!", car.getLabel() + " crossed the finish line first!");
        }
        if (model.isRaceFinished()) {
            state = State.FINISHED;
            showFinalResults();
            updateButtons();
        }
    }

    private void showFinalResults() {
        Car winner = model.getWinner();
        messageTitleLabel.setText("Race Finished!");
        messageLabel.setText("Congratulations! " + winner.getLabel() + " is the winner!");
        resultsBox.getChildren().setAll(view.createResultRows(model.getFinishOrder()));
    }

    private void changeSpeed(double value) {
        speedValueLabel.setText(String.format("%.1fx", value));
        moves.forEach(t -> t.setRate(value));
        spins.forEach(t -> t.setRate(value));
    }

    private void showMessage(String title, String text) {
        messageTitleLabel.setText(title);
        messageLabel.setText(text);
        resultsBox.getChildren().clear();
        infoLabel.setText("");
    }

    private void updateButtons() {
        startButton.setDisable(!(state == State.READY || state == State.PAUSED));
        pauseButton.setDisable(state != State.RUNNING);
    }

    public void shutdown() {
        if (player != null) {
            player.stop();
        }
        moves.forEach(Animation::stop);
        spins.forEach(Animation::stop);
    }
}
