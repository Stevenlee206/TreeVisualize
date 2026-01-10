package com.example.treevisualize.demo;

import com.example.treevisualize.Model.Description.TreeType;
import com.example.treevisualize.View.Screen.InfoScreen;
import com.example.treevisualize.View.Screen.IntroScreen;
import com.example.treevisualize.View.Screen.SelectScreen;
import com.example.treevisualize.View.Screen.VisualizeScreen;
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
    private TreeType selectedTreeType = TreeType.BST;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        primaryStage.setTitle("Tree Visualization App");
        primaryStage.setMaximized(true);

        try {
            primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/TreeLogo.png"))));
        } catch (Exception ignored) { }

        switchToIntroScreen();
        primaryStage.show();
    }

    public void switchToIntroScreen() { new IntroScreen(this).show(); }

    public void switchToSelectScreen() { new SelectScreen(this).show(); }

    public void switchToInfoScreen() { new InfoScreen(this).show(); }

    public void switchToVisualizerScreen() { new VisualizeScreen(this).show(); }

    public void switchScene(Parent root) {
        if (primaryStage.getScene() == null) {
            Scene scene = new Scene(root,1000,700);
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            primaryStage.setScene(scene);
        } else {
        	root.setOpacity(0);
        	FadeTransition ft = new FadeTransition(Duration.millis(400), root);
            ft.setFromValue(0.0);
            ft.setToValue(1.0);
            ft.play();
            primaryStage.getScene().setRoot(root);
        }
    }

    public void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public TreeType getSelectedTreeType() {
        return selectedTreeType;
    }

    public void setSelectedTreeType(TreeType selectedTreeType) {
        this.selectedTreeType = selectedTreeType;
        System.out.println("Main Log: User selected tree -> " + selectedTreeType);
    }
}

/*
cd "D:/File_In_OS(C)/Documents/TreeVisualizeCurrent"

$FX = "D:/File_In_OS(C)/Downloads/openjfx-25.0.1_windows-x64_bin-sdk/javafx-sdk-25.0.1/lib"

$javaFiles = Get-ChildItem -Recurse ./src/main/java -Filter *.java | Select-Object -ExpandProperty FullName
javac --module-path $FX --add-modules javafx.controls,javafx.fxml -d bin $javaFiles

java --module-path $FX --add-modules javafx.controls,javafx.fxml,javafx.graphics --enable-native-access=javafx.graphics -cp "bin" com.example.treevisualize.demo.Main5
*/