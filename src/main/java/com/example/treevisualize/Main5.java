package com.example.treevisualize;

import com.example.treevisualize.Screen.InfoScreen;
import com.example.treevisualize.Screen.IntroScreen;
import com.example.treevisualize.Screen.SelectScreen;
import com.example.treevisualize.Screen.VisualizeScreen;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;

public class Main5 extends Application {

    private Stage primaryStage;
    private String selectedTreeType = "Red Black Tree";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        primaryStage.setTitle("Tree Visualization App - Ultimate Edition");
        primaryStage.setMaximized(true);

        try {
            primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/TreeLogo.png"))));
        } catch (Exception ignored) { }

        switchToIntroScreen();

        primaryStage.show();
    }

    public void switchToIntroScreen() {
        new IntroScreen(this).show();
    }

    public void switchToSelectScreen() {
        new SelectScreen(this).show();
    }

    public void switchToInfoScreen() {
        new InfoScreen(this).show();
    }

    public void switchToVisualizerScreen() {
        new VisualizeScreen(this).show();
    }

    public void switchScene(Parent root, double width, double height) {
        Scene scene = new Scene(root, width, height);

        try {
            String css = Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm();
            scene.getStylesheets().add(css);
        } catch (Exception e) {
            System.err.println("Error: file style.css not found!");
        }

        root.setOpacity(0);
        FadeTransition ft = new FadeTransition(Duration.millis(400), root);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();

        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    public void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public String getSelectedTreeType() {
        return selectedTreeType;
    }

    public void setSelectedTreeType(String selectedTreeType) {
        this.selectedTreeType = selectedTreeType;
        System.out.println("Main3 Log: User selected tree -> " + selectedTreeType);
    }
}