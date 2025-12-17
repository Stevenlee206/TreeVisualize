package com.example.treevisualize.Screen;

import com.example.treevisualize.Description.TreeType;
import com.example.treevisualize.Main5;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class InfoScreen {

    private final Main5 mainApp;

    public InfoScreen(Main5 mainApp) {
        this.mainApp = mainApp;
    }

    public void show() {
        // Lấy Enum hiện tại
        TreeType type = mainApp.getSelectedTreeType();

        BorderPane root = new BorderPane();
        root.getStyleClass().add("info-pane");

        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER_LEFT);
        content.setMaxWidth(800);

        Label lblHeading = new Label(type.getDisplayName());
        lblHeading.getStyleClass().add("info-heading");

        // [OOP] Gọi hàm của Enum để lấy mô tả
        Text txtDesc = new Text(type.getDescriptionText());
        txtDesc.getStyleClass().add("info-desc");
        txtDesc.setWrappingWidth(700);

        HBox actions = new HBox(20);

        Button btnBack = new Button("⬅ Back");
        btnBack.getStyleClass().add("button");
        btnBack.setOnAction(e -> mainApp.switchToSelectScreen());

        Button btnStart = new Button("START VISUALIZE 🚀");
        btnStart.getStyleClass().add("btn-primary");
        btnStart.setStyle("-fx-font-size: 16px; -fx-padding: 10 25;");
        btnStart.setOnAction(e -> mainApp.switchToVisualizerScreen());

        actions.getChildren().addAll(btnBack, btnStart);
        content.getChildren().addAll(lblHeading, txtDesc, new Separator(), actions);

        root.setCenter(content);
        mainApp.switchScene(root, 1100, 750);
    }
}