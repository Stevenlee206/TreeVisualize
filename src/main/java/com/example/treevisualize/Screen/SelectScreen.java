package com.example.treevisualize.Screen;

import com.example.treevisualize.Main5;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import java.util.Objects;

public class SelectScreen {
    private final Main5 mainApp;

    public SelectScreen(Main5 mainApp) {
        this.mainApp = mainApp;
    }

    public void show() {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("selection-pane");
        root.setPadding(new Insets(20));

        Button btnBack = new Button("⬅ Back");
        btnBack.getStyleClass().add("button");
        btnBack.setOnAction(e -> mainApp.switchToIntroScreen());

        VBox titleBox = new VBox(5);
        titleBox.setAlignment(Pos.CENTER);
        Label lblHeader = new Label("Choose your tree");
        lblHeader.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        Label lblSub = new Label("Select a structure to visualize immediately");
        lblSub.setStyle("-fx-font-size: 16px; -fx-text-fill: #7f8c8d;");
        titleBox.getChildren().addAll(lblHeader, lblSub);

        BorderPane topHeader = new BorderPane();
        topHeader.setLeft(btnBack);
        topHeader.setCenter(titleBox);
        BorderPane.setAlignment(titleBox, Pos.CENTER);
        topHeader.setPadding(new Insets(0, 0, 20, 0));

        root.setTop(topHeader);

        GridPane grid = createGrid();

        VBox gridContainer = new VBox(grid);
        gridContainer.setAlignment(Pos.CENTER);

        root.setCenter(gridContainer);

        mainApp.switchScene(root, 1200, 850);
    }

    private GridPane createGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(20);

        ColumnConstraints colConst = new ColumnConstraints();
        colConst.setPercentWidth(33.33);
        grid.getColumnConstraints().addAll(colConst, colConst, colConst);

        RowConstraints rowConst = new RowConstraints();
        rowConst.setPercentHeight(33.33);
        grid.getRowConstraints().addAll(rowConst, rowConst, rowConst);

        grid.setMaxSize(1000, 600);

        grid.add(createSelectableCard("Red Black Tree", "Self-Balancing Binary Tree.", "/images/RBT_icon.png"), 0, 0);
        grid.add(createSelectableCard("Binary Search Tree", "Standard BST Structure.", "/images/BST_icon.png"), 1, 0);
        grid.add(createSelectableCard("Binary Tree (Normal)", "Basic Level-Order Tree.", "/images/BT_icon.png"), 2, 0);

        grid.add(createSelectableCard("General Tree", "N-ary Tree (Multiple children).", "/images/GT_icon.png"), 0, 1);
        grid.add(createEmptySlot(), 1, 1);
        grid.add(createEmptySlot(), 2, 1);

        grid.add(createEmptySlot(), 0, 2);
        grid.add(createEmptySlot(), 1, 2);
        grid.add(createEmptySlot(), 2, 2);

        return grid;
    }

    private VBox createSelectableCard(String title, String desc, String iconPath) {
        VBox card = new VBox(10);
        card.getStyleClass().add("tree-card");
        card.setAlignment(Pos.CENTER);

        card.setUserData(title);

        card.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        GridPane.setFillWidth(card, true);
        GridPane.setFillHeight(card, true);

        ImageView iconView = new ImageView();
        try {
            String path = (iconPath != null) ? iconPath : "/images/TreeView.png";
            Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)));
            iconView.setImage(img);
            iconView.setFitHeight(50);
            iconView.setPreserveRatio(true);
        } catch (Exception e) {
        	
        }

        Label lblTitle = new Label(title);
        lblTitle.getStyleClass().add("card-title");
        lblTitle.setTextAlignment(TextAlignment.CENTER);
        lblTitle.setWrapText(true);

        Label lblDesc = new Label(desc);
        lblDesc.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 12px;");
        lblDesc.setTextAlignment(TextAlignment.CENTER);
        lblDesc.setWrapText(true);

        card.getChildren().addAll(iconView, lblTitle, lblDesc);

        card.setOnMouseClicked(e -> {
            mainApp.setSelectedTreeType(title);
            mainApp.switchToInfoScreen();
        });
        card.setStyle("-fx-cursor: hand;");

        return card;
    }

    private VBox createEmptySlot() {
        VBox slot = new VBox();
        slot.setStyle("-fx-border-color: #bdc3c7; -fx-border-width: 2; -fx-border-style: dashed; -fx-background-color: rgba(255,255,255,0.3); -fx-background-radius: 15; -fx-border-radius: 15;");
        slot.setAlignment(Pos.CENTER);
        slot.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        Label lbl = new Label("Coming Soon");
        lbl.setStyle("-fx-text-fill: #95a5a6; -fx-font-style: italic;");
        slot.getChildren().add(lbl);

        return slot;
    }
}