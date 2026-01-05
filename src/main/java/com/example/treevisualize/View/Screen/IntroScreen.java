package com.example.treevisualize.View.Screen;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import com.example.treevisualize.demo.Main5;

import java.util.Objects;

public class IntroScreen {
    private final Main5 mainApp;

    public IntroScreen(Main5 mainApp) {
        this.mainApp = mainApp;
    }
    public void show() {
        StackPane root = new StackPane();
        root.getStyleClass().add("intro-pane");

        Pane decorationLayer = new Pane();
        decorationLayer.setPickOnBounds(false);

        addDecoration(root, "/images/TreeView.png", 195, -300, -181);
        addDecoration(root, "/images/TreeLogo.png", 195, 300, 140);
        addDecoration(root, "/images/BFS.png", 230, -340, 190);
        addDecoration(root, "/images/DFS.png", 230, -100, 190);

        VBox uiLayer = new VBox(20);
        uiLayer.setAlignment(Pos.CENTER);
        uiLayer.setTranslateY(-105);
        uiLayer.setPickOnBounds(false);

        Label title = new Label("Tree Data Structure\nVisualization");
        title.getStyleClass().add("intro-title");
        title.setTextAlignment(TextAlignment.CENTER);

        Label subtitle = new Label("Explore how Tree data structure work in an intuitive and vivid way.");
        subtitle.getStyleClass().add("intro-subtitle");
        subtitle.setTranslateY(30);

        Button btnNext = new Button("Start  ➜");
        btnNext.getStyleClass().add("btn-intro-next");
        btnNext.setOnAction(e -> mainApp.switchToSelectScreen());
        btnNext.setTranslateY(50);

        uiLayer.getChildren().addAll(title, subtitle, btnNext);
        root.getChildren().add(uiLayer);

        mainApp.switchScene(root, 1000, 700);
    }

    private void addDecoration(StackPane root, String path, double width, double x, double y) {
        try {
            Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)));
            ImageView view = new ImageView(img);
            view.setFitHeight(width);
            view.setPreserveRatio(true);
            view.getStyleClass().add("intro-image");
            view.setTranslateX(x);
            view.setTranslateY(y);

            root.getChildren().add(0, view);
        } catch (Exception e) {
            System.err.println("Picture not found: " + path);
        }
    }

}
